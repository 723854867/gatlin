package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private ResourceService resourceService;

	@ResponseBody
	@RequestMapping("list")
	public Object users(@RequestBody @Valid SoaParam param) {
		Pager<UserInfo> pager = userService.users(param.query());
		return null;
	}
}
