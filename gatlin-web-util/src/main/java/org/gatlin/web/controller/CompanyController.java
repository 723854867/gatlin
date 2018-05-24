package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("company")
public class CompanyController {
	
	@Resource
	private CompanyService companyService;

	@ResponseBody
	@RequestMapping("add")
	public Object add(@RequestBody @Valid CompanyAddParam param) {
		return companyService.add(param);
	}
}
