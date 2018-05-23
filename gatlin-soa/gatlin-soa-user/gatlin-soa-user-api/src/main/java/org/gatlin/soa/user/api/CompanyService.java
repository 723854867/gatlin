package org.gatlin.soa.user.api;

import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.param.CompanyAddParam;

public interface CompanyService {

	int add(CompanyAddParam param);
	
	Company company(int companyId);
	
	// 企业账户提现至个人账户校验
	void withdraw2userVerify(int companyId, long uid);
}
