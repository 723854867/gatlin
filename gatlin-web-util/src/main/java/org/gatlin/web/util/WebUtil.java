package org.gatlin.web.util;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.util.lang.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtil {
	
	public static final HttpServletRequest getRequest() {
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attribute.getRequest();
	}
	
	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 */
	public static final String getIpAddress(HttpServletRequest request) throws IOException {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtil.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (!StringUtil.hasText(ip) || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("WL-Proxy-Client-IP");
			if (!StringUtil.hasText(ip) || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("HTTP_CLIENT_IP");
			if (!StringUtil.hasText(ip) || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			if (!StringUtil.hasText(ip) || "unknown".equalsIgnoreCase(ip))
				ip = request.getRemoteAddr();
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
	public static Class<?> returnType(ProceedingJoinPoint point) throws Exception {
		String name = point.getSignature().getName();
		Method[] methods = point.getTarget().getClass().getMethods();
		for (Method method : methods) {
			if (!method.getName().equals(name))
				continue;
			return method.getReturnType();
		}
		throw new CodeException(CoreCode.SYSTEM_ERR);
	}
	
	public static Method method(ProceedingJoinPoint point) throws Exception {
		String name = point.getSignature().getName();
		Method[] methods = point.getTarget().getClass().getMethods();
		for (Method method : methods) {
			if (!method.getName().equals(name))
				continue;
			return method;
		}
		throw new CodeException(CoreCode.SYSTEM_ERR);
	}
}
