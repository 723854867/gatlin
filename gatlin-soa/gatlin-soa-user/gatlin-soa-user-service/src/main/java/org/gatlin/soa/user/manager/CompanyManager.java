package org.gatlin.soa.user.manager;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.soa.user.bean.model.EmployeeInfo;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.bean.param.EmployeeAddParam;
import org.gatlin.soa.user.bean.param.EmployeeModifyParam;
import org.gatlin.soa.user.bean.param.EmployeesParam;
import org.gatlin.soa.user.mybatis.EntityGenerator;
import org.gatlin.soa.user.mybatis.dao.CompanyDao;
import org.gatlin.soa.user.mybatis.dao.EmployeeDao;
import org.gatlin.util.DateUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class CompanyManager {

	@Resource
	private CompanyDao companyDao;
	@Resource
	private EmployeeDao employeeDao;
	
	public int add(CompanyAddParam param) {
		try {
			Company company = EntityGenerator.newCompany(param);
			companyDao.insert(company);
			return company.getId();
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.COMPANY_EIXST);
		}
	}
	
	public long employeeAdd(EmployeeAddParam param) { 
		Employee employee = EntityGenerator.newEmployee(param);
		try {
			employeeDao.insert(employee);
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.EMPLOYEE_EXIST);
		}
		return employee.getId();
	}
	
	public void employeeModify(EmployeeModifyParam param) { 
		Employee employee = employeeDao.getByKey(param.getId());
		Assert.notNull(UserCode.EMPLOYEE_NO_EXIST, employee);
		if (null != param.getState())
			employee.setState(param.getState());
		employee.setUpdated(DateUtil.current());
		employeeDao.update(employee);
	}
	
	public List<EmployeeInfo> employees(EmployeesParam param) { 
		return employeeDao.list(param);
	}
	
	public Company company(int companyId) {
		return companyDao.getByKey(companyId);
	}
	
	public Employee employee(long employeeId) {
		return employeeDao.getByKey(employeeId);
	}
}
