package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.param.CompanyRechargesParam;
import org.gatlin.soa.account.bean.param.RechargesParam;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.soa.user.bean.param.EEmployeesParam;
import org.gatlin.soa.user.bean.param.EmployeesParam;
import org.gatlin.web.Checker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("employee")
public class EmployeeController {
	
	@Resource
	private Checker checker;
	@Resource
	private CompanyService companyService;
	@Resource
	private AccountService accountService;

	@ResponseBody
	@RequestMapping("employees")
	public Object employees(@RequestBody @Valid EEmployeesParam param) {
		Employee employee = checker.employeeVerify(param);
		EmployeesParam ep = new EmployeesParam();
		ep.setName(param.getName());
		ep.setMobile(param.getMobile());
		ep.setId(param.getEmployeeId());
		ep.setIdentity(param.getIdentity());
		ep.setCompanyId(employee.getCompanyId());
		return companyService.employees(ep);
	}
	
	@ResponseBody
	@RequestMapping("recharges")
	public Object recharges(@RequestBody @Valid CompanyRechargesParam param) {
		Employee employee = checker.employeeVerify(param);
		RechargesParam rp = new RechargesParam();
		BeanUtils.copyProperties(param, rp);
		rp.setId(param.getRechargeId());
		rp.setRechargeeType(TargetType.COMPANY);
		rp.setRechargee(Long.valueOf(employee.getCompanyId()));
		rp.verify();
		return accountService.recharges(rp.query());
	}
}
