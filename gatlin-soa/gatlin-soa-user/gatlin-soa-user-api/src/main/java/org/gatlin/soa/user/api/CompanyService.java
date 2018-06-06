package org.gatlin.soa.user.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.soa.user.bean.model.EmployeeInfo;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.bean.param.EmployeeAddParam;
import org.gatlin.soa.user.bean.param.EmployeeModifyParam;
import org.gatlin.soa.user.bean.param.EmployeesParam;

public interface CompanyService {

	int add(CompanyAddParam param);
	
	Company company(int companyId);
	
	Employee employee(long employeeId);
	
	long employeeAdd(EmployeeAddParam param);
	
	void employeeModify(EmployeeModifyParam param);
	
	Pager<EmployeeInfo> employees(EmployeesParam param);
}
