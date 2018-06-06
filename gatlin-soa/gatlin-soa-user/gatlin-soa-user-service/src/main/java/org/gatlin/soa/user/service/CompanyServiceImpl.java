package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.soa.user.bean.model.EmployeeInfo;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.bean.param.EmployeeAddParam;
import org.gatlin.soa.user.bean.param.EmployeeModifyParam;
import org.gatlin.soa.user.bean.param.EmployeesParam;
import org.gatlin.soa.user.manager.CompanyManager;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	
	@Resource
	private UserService userService;
	@Resource
	private CompanyManager companyManager;

	@Override
	public int add(CompanyAddParam param) {
		return companyManager.add(param);
	}
	
	@Override
	public Company company(int companyId) {
		return companyManager.company(companyId);
	}
	
	@Override
	public Employee employee(long employeeId) {
		return companyManager.employee(employeeId);
	}
	
	@Override
	public Pager<Company> companies(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<Company>(companyManager.companies(query));
	}
	
	@Override
	public long employeeAdd(EmployeeAddParam param) {
		User user = userService.user(param.getUid());
		Assert.notNull(UserCode.USER_NOT_EIXST, user);
		Company company = companyManager.company(param.getId());
		Assert.notNull(UserCode.COMPANY_NOT_EIXST, company);
		return companyManager.employeeAdd(param);
	}
	
	@Override
	public void employeeModify(EmployeeModifyParam param) {
		companyManager.employeeModify(param);
	}
	
	@Override
	public Pager<EmployeeInfo> employees(EmployeesParam param) {
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return new Pager<EmployeeInfo>(companyManager.employees(param));
	}
}
