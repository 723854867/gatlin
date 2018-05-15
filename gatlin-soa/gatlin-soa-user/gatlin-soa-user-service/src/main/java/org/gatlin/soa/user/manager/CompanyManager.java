package org.gatlin.soa.user.manager;

import javax.annotation.Resource;

import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.mybatis.EntityGenerator;
import org.gatlin.soa.user.mybatis.dao.CompanyDao;
import org.springframework.stereotype.Component;

@Component
public class CompanyManager {

	@Resource
	private CompanyDao companyDao;
	
	public int add(CompanyAddParam param) {
		Company company = EntityGenerator.newCompany(param);
		companyDao.insert(company);
		return company.getId();
	}
}
