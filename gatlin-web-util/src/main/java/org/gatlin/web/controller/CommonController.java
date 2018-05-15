package org.gatlin.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.entity.CfgGlobal;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.param.CfgGlobalParam;
import org.gatlin.soa.courier.api.EmailService;
import org.gatlin.soa.courier.api.SmsService;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.enums.ResourceType;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.UserUtil;
import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.enums.UserMod;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.soa.user.bean.param.PwdModifyParam;
import org.gatlin.soa.user.bean.param.PwdResetParam;
import org.gatlin.soa.user.bean.param.UsernameParam;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.bean.model.UserTips;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("common")
public class CommonController {
	
	@Resource
	private SmsService smsService;
	@Resource
	private UserService userService;
	@Resource
	private EmailService emailService;
	@Resource
	private ConfigService configService;
	@Resource
	private ResourceService resourceService;
	
	@ResponseBody
	@RequestMapping("captcha/obtain")
	public Object captchaObtain(@RequestBody @Valid UsernameParam param) {
		switch (param.getUsernameType()) {
		case EMAIL:
			return emailService.captchaAcquire(param.getUsername());
		case MOBILE:
			return smsService.captchaAcquire(param.getUsername());
		default:
			throw new CodeException(CoreCode.FORBID);
		}
	}

	@ResponseBody
	@RequestMapping("login")
	public Object login(@RequestBody @Valid LoginParam param) {
		LoginModel model = userService.login(param);
		UserDevice odevice = model.getOdevice();
		if (null != odevice) {				// 需要判断老设备和新设备是否是同一个设备，如果不是同一个设备则需要给老设备推送离线通知
			// TODO: 换设备登录推送
		}
		return model.getInfo();
	}
	
	@ResponseBody
	@RequestMapping("logout")
	public Object logout(@RequestBody @Valid SoaParam param) {
		userService.logout(param.getToken());
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("pwd/reset")
	public Object pwdReset(@RequestBody @Valid PwdResetParam param) {
		User user = userService.user(param.getUsernameType(), param.getUsername());
		Assert.notNull(UserCode.USERNAME_NOT_EXIST, user);
		switch (param.getUsernameType()) {
		case EMAIL:
			emailService.captchaVerify(param.getUsername(), param.getCaptcha());
			break;
		case MOBILE:
			smsService.captchaVerify(param.getUsername(), param.getCaptcha());
			break;
		case COMMON:
			String cpwd = UserUtil.pwd(param.getOpassword(), param.getUser().getSalt());
			Assert.isTrue(UserCode.LOGIN_PWD_ERROR, cpwd.equalsIgnoreCase(param.getUser().getPwd()));
			break;
		default:
			throw new CodeException(CoreCode.FORBID);
		}
		user.setPwd(UserUtil.pwd(param.getPassword(), user.getSalt()));
		userService.update(user);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("pwd/modify")
	public Object pwdModify(@RequestBody @Valid PwdModifyParam param) {
		User user = param.getUser();
		String cpwd = UserUtil.pwd(param.getOpwd(), param.getUser().getSalt());
		Assert.isTrue(UserCode.LOGIN_PWD_ERROR, cpwd.equalsIgnoreCase(param.getUser().getPwd()));
		user.setPwd(UserUtil.pwd(param.getPwd(), param.getUser().getSalt()));
		userService.update(user);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("user/mod")
	public Object userMod(@RequestBody @Valid UsernameParam param) {
		User user = userService.user(param.getUsernameType(), param.getUsername());
		int mod = 0;
		if (null == user)
			return mod;
		mod |= UserMod.EXIST.mark();
		if (!StringUtil.hasText(user.getPwd()))
			mod |= UserMod.PWD_UNSET.mark();
		return mod;
	}
	
	@ResponseBody
	@RequestMapping("user/tips")
	public Object userTips(@RequestBody @Valid SoaParam param) {
		User user = param.getUser();
		Query query = new Query().eq("cfg_id", ResourceType.AVATAR.mark()).eq("owner", user.getId());
		return new UserTips(user, resourceService.resource(query));
	}

	@ResponseBody
	@RequestMapping("configs")
	public Object configs(@RequestBody @Valid SoaParam param) {
		Configs configs = configService.configs(new Query());
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<String, CfgGlobal> entry : configs.getGlobals().entrySet()) 
			map.put(entry.getKey(), entry.getValue().getValue());
		return map;
	}
	
	@ResponseBody
	@RequestMapping("configs/update")
	public Object configs(@RequestBody @Valid CfgGlobalParam param) {
		configService.configUpdate(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("configs/visible")
	public Object configsVisible(@RequestBody @Valid SoaParam param) {
		Configs configs = configService.configs(new Query().eq("visible", 1));
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<String, CfgGlobal> entry : configs.getGlobals().entrySet()) 
			map.put(entry.getKey(), entry.getValue().getValue());
		return map;
	}
}
