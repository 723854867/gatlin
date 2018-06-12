package org.gatlin.web;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.soa.user.bean.enums.EmployeeState;
import org.springframework.stereotype.Component;

@Component
public class Checker {

	@Resource
	private CompanyService companyService;
	
	public Employee employeeVerify(SoaLidParam param) { 
		Employee employee = companyService.employee(param.getId());
		Assert.notNull(UserCode.EMPLOYEE_NO_EXIST, employee);
		Assert.isTrue(CoreCode.FORBID, employee.getUid() == param.getUser().getId());
		Assert.isTrue(CoreCode.FORBID, employee.getState() == EmployeeState.NORMAL);
		return employee;
	}
}
