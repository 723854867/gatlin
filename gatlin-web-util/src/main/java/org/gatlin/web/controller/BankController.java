package org.gatlin.web.controller;

import javax.validation.Valid;

import org.gatlin.soa.bean.param.SoaParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("bank")
public class BankController {

	@ResponseBody
	@RequestMapping("card/bind")
	public Object cardBind(@RequestBody @Valid SoaParam param) {
		return null;
	}
}
