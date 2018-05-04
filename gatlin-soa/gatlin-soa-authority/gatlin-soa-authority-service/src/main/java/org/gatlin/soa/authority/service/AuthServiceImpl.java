package org.gatlin.soa.authority.service;

import java.util.Set;

import javax.annotation.Resource;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.authority.api.AuthService;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ApiModifyParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.soa.authority.bean.param.NameIdParam;
import org.gatlin.soa.authority.manager.AuthManager;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	
	@Resource
	private AuthManager authManager;

	@Override
	public CfgApi api(Query query) {
		return authManager.api(query);
	}
	
	@Override
	public int apiAdd(ApiAddParam param) {
		return authManager.apiAdd(param);
	}
	
	@Override
	public void apiModify(ApiModifyParam param) {
		
	}
	
	@Override
	public void apiDelete(SoaIdParam param) {
		
	}
	
	@Override
	public int modularAdd(ModularAddParam param) {
		return authManager.modularAdd(param);
	}
	
	@Override
	public void modularModify(NameIdParam param) {
		authManager.modularModify(param);
	}
	
	@Override
	public Set<Integer> modularDelete(SoaIdParam param) {
		return authManager.modularDelete(param);
	}
	
	@Override
	public int roleAdd(SoaSidParam param) {
		return authManager.roleAdd(param);
	}
	
	@Override
	public void roleDelete(SoaIdParam param) {
		authManager.roleDelete(param);
	}
	
	@Override
	public void roleModify(NameIdParam param) {
		
	}
}
