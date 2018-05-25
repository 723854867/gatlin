package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.SpringContextUtil;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.jpush.api.JPushService;
import org.gatlin.soa.jpush.bean.entity.JPushDevice;
import org.gatlin.web.util.IJPushRegisterHook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jpush")
public class JPushController {
	
	@Resource
	private JPushService jPushService;

	@ResponseBody
	@RequestMapping("register")
	public Object register(@RequestBody @Valid SoaSidParam param) {
		JPushDevice odevice = jPushService.bind(param);
		if (null != odevice) {
			IJPushRegisterHook hook = SpringContextUtil.getBeanOfType(IJPushRegisterHook.class, false, true);
			if (null != hook)
				hook.register(param.getId(), odevice);
		}
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("unregister")
	public Object unregister(@RequestBody @Valid SoaParam param) {
		jPushService.unbind(param);
		return Response.ok();
	}
}
