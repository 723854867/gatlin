package org.gatlin.web.util.aop;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.core.bean.model.message.Request;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.bean.model.message.WrapResponse;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.authority.api.AuthService;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.log.api.LogService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.gatlin.web.WebConsts;
import org.gatlin.web.util.GatewayHook;
import org.gatlin.web.util.WebCode;
import org.gatlin.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonParser;

@Aspect
@Component
public class GatewayInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(GatewayInterceptor.class);

	@Autowired(required = false)
	private LogService logService;
	@Autowired(required = false)
	private UserService userService;
	@Autowired(required = false)
	private AuthService authService;
	@Autowired(required = false)
	private GatewayHook gatewayHook;
	@Resource
	private ConfigService configService;

	@Pointcut("execution(* org..controller.*.*(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object controllerAround(ProceedingJoinPoint point) throws Throwable {
		Method method = WebUtil.method(point);
		if (!method.isAnnotationPresent(RequestMapping.class))
			return point.proceed();
		HttpServletRequest request = WebUtil.getRequest();
		LogRequest log = _logRequest(request, point);
		int serverState = configService.config(WebConsts.Options.SERVER_STATE);
		CfgApi api = null != authService ? authService.api(new Query().eq("path", request.getServletPath())) : null;
		int apiSecurityLevel = null == api ? 1 : api.getSecurityLevel();
		Assert.isTrue(WebCode.API_MAINTENANCE, apiSecurityLevel > serverState);
		if (null != api)
			log.setDesc(api.getDesc());
		// 用户数据处理
		User user = null;
		String token = request.getHeader("Token");
		if (StringUtil.hasText(token)) {
			if (null != api && api.isSerial()) 
				user = null != userService ? userService.lock(token, api.getLockTimeout()) : null;
			else
				user = null != userService ? userService.user(token) : null;
		}
		try {
			log.setOperator(user);
			Object[] params = point.getArgs();
			for (Object param : params) {
				if (param instanceof Request) {
					Request req = (Request) param;
					req.init(log, token, user);
					req.verify();
				}
			}
			if (null != api) {
				if (api.isLogin()) {			// 检测登录
					Assert.notNull(UserCode.USER_UNLOGIN, user);
					// 检测授权
					if (null != authService)
						authService.auth(user, api);
				}
				if (0 != api.getDeviceMod()) {
					DeviceType deviceType = user.getDeviceType();
					Assert.isTrue(UserCode.DEVICE_UNSUPPORT, (api.getDeviceMod() & deviceType.mark()) == deviceType.mark());
				}
			}
			if (null != gatewayHook)
				gatewayHook.filter(user, params);
			Object result = point.proceed();
			Object response = null;
			if (null != result) {
				if (result instanceof WrapResponse)
					response = ((WrapResponse) result).getResult();
				else if (!(result instanceof Response<?>))
					response = new Response<Object>(result);
				else
					response = result;
			} else
				response = Response.ok();
			log.setResponse(new JsonParser().parse(SerializeUtil.GSON.toJson(response)));
			log.setSuccess(true);
			log.setRtime(DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
			return response;
		} catch (Exception e) {
			StringWriter error = new StringWriter();
			log.setSuccess(false);
			e.printStackTrace(new PrintWriter(error));
			log.setResponse(error.toString());
			log.setRtime(DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
			throw e;
		} finally {
			if (null != user && StringUtil.hasText(user.getLockId())) {
				boolean released = userService.releaseLock(user.getId(), user.getLockId());
				if (!released)
					logger.warn("用户  {} 锁资源释放失败！", user.getId());
			}
			// 开启日志记录
			if (null != api) {
				 switch (api.getStorageType()) {
				case FILE:
					logger.info("客户端请求：{}", SerializeUtil.GSON.toJson(log));
					break;
				case MONGO:
					if (null != logService)
						logService.logRequest(log);
					break;
				default:
					break;
				}
			}
		}
	}

	private final LogRequest _logRequest(HttpServletRequest request, ProceedingJoinPoint point) throws IOException {
		LogRequest meta = new LogRequest();
		meta.set_id(IDWorker.INSTANCE.nextSid());
		meta.setIp(WebUtil.getIpAddress(request));
		meta.setCtime(DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		meta.setType(request.getMethod());
		meta.setPath(request.getRequestURI().toString());
		meta.setQuery(request.getQueryString());
		Object[] params = point.getArgs();
		List<Object> list = new ArrayList<Object>();
		for(int i = 0 ; i < params.length ; i++) {
			if (params[i] instanceof InputStream || params[i] instanceof InputStreamSource)
				continue;
			if(params[i] instanceof Serializable) 
				list.add(params[i]);
		}
		Object object = list.size() == 1 ? list.iterator().next() : list.toArray(new Object[] {});
		meta.setParam(new JsonParser().parse(SerializeUtil.GSON.toJson(object)));
		String method = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
		meta.setMethod(method);
		meta.setCreated(DateUtil.current());
		return meta;
	}
}
