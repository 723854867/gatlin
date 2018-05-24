package org.gatlin.soa.user.manager;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.mybatis.EntityGenerator;
import org.gatlin.soa.user.mybatis.dao.CompanyDao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class CompanyManager {

	@Resource
	private CompanyDao companyDao;
	
	public int add(CompanyAddParam param) {
		try {
			Company company = EntityGenerator.newCompany(param);
			companyDao.insert(company);
			return company.getId();
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.COMPANY_EIXST);
		}
	}
	
	public Company company(int companyId) {
		return companyDao.getByKey(companyId);
	}
}
