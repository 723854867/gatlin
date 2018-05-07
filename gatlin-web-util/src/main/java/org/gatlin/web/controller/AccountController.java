package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.soa.bean.param.SoaIdsParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@ResponseBody
	@RequestMapping("user/list")
	public Object list(@RequestBody @Valid SoaIdsParam param) {
		return null;
	}
}
