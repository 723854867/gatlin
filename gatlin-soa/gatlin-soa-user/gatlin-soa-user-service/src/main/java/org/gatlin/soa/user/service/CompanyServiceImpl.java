package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.param.CompanyAddParam;
import org.gatlin.soa.user.manager.CompanyManager;
import org.springframework.stereotype.Service;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	
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
}
