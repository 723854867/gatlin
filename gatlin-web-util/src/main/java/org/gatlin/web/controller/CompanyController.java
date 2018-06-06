package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.bean.param.CompaniesParam;
import org.gatlin.soa.user.bean.param.EmployeeAddParam;
import org.gatlin.soa.user.bean.param.EmployeeModifyParam;
import org.gatlin.soa.user.bean.param.EmployeesParam;
import org.gatlin.web.WebConsts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("company")
public class CompanyController {
	
	@Resource
	private CompanyService companyService;
	private AccountService accountService;

	@ResponseBody
	@RequestMapping("add")
	public Object add(@RequestBody @Valid CompanyAddParam param) {
		int companyId = companyService.add(param);
		int accountMod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_MOD_COMPANY);
		if (0 != accountMod)
			accountService.init(TargetType.COMPANY, companyId, accountMod);
		return companyId;
	}
	
	@ResponseBody
	@RequestMapping("list")
	public Object list(@RequestBody @Valid CompaniesParam param) {
		return companyService.companies(param.query());
	}

	@ResponseBody
	@RequestMapping("employees")
	public Object employees(@RequestBody @Valid EmployeesParam param) {
		return companyService.employees(param);
	}
	
	@ResponseBody
	@RequestMapping("employee/add")
	public Object employeeAdd(@RequestBody @Valid EmployeeAddParam param) {
		return companyService.employeeAdd(param);
	}
	
	@ResponseBody
	@RequestMapping("employee/modify")
	public Object employeeModify(@RequestBody @Valid EmployeeModifyParam param) {
		companyService.employeeModify(param);
		return Response.ok();
	}
}
