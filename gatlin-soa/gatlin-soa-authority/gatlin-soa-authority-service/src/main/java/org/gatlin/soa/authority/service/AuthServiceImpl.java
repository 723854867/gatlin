package org.gatlin.soa.authority.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.authority.api.AuthService;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.entity.CfgModular;
import org.gatlin.soa.authority.bean.entity.CfgRole;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ApiModifyParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.soa.authority.bean.param.ModularModifyParam;
import org.gatlin.soa.authority.bean.param.NameIdParam;
import org.gatlin.soa.authority.manager.AuthManager;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.soa.bean.param.SoaIdsParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	
	@Resource
	private AuthManager authManager;

	@Override
	public CfgApi api(Query query) {
		return authManager.api(query);
	}
	
	@Override
	public Pager<CfgApi> apis(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		List<CfgApi> list = authManager.apis(query);
		return new Pager<CfgApi>(list);
	}
	
	@Override
	public int apiAdd(ApiAddParam param) {
		return authManager.apiAdd(param);
	}
	
	@Override
	public void apiModify(ApiModifyParam param) {
		authManager.apiModify(param);
	}
	
	@Override
	public void apiDelete(SoaIdParam param) {
		authManager.apiDelete(param);
	}
	
	@Override
	public List<CfgModular> modulars() {
		return authManager.modulars();
	}
	
	@Override
	public int modularAdd(ModularAddParam param) {
		return authManager.modularAdd(param);
	}
	
	@Override
	public void modularModify(ModularModifyParam param) {
		authManager.modularModify(param);
	}
	
	@Override
	public void modularDelete(SoaIdsParam param) {
		authManager.modularDelete(param);
	}
	
	@Override
	public Pager<CfgRole> roles(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<CfgRole>(authManager.roles(query));
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
		authManager.roleModify(param);
	}
}
