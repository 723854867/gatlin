package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.model.message.Response;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.user.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jpush")
public class JPushController {
	
	@Resource
	private UserService userService;

	@ResponseBody
	@RequestMapping("register")
	public Object register(@RequestBody @Valid SoaSidParam param) {
		userService.bind(param.getUser().getId(), PlatType.JPUSH, param.getId());
		return Response.ok();
	}
}
