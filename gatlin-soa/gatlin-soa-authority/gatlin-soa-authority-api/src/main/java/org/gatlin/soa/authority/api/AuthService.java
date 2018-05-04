package org.gatlin.soa.authority.api;

import java.util.List;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.entity.CfgModular;
import org.gatlin.soa.authority.bean.entity.CfgRole;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ApiModifyParam;
import org.gatlin.soa.authority.bean.param.AuthParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.soa.authority.bean.param.ModularModifyParam;
import org.gatlin.soa.authority.bean.param.NameIdParam;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.soa.bean.param.SoaIdsParam;
import org.gatlin.soa.bean.param.SoaSidParam;

public interface AuthService {

	CfgApi api(Query query);
	
	Pager<CfgApi> apis(Query query);
	
	int apiAdd(ApiAddParam param);
	
	void apiModify(ApiModifyParam param);
	
	void apiDelete(SoaIdParam param);
	
	List<CfgModular> modulars();
	
	int modularAdd(ModularAddParam param);
	
	void modularModify(ModularModifyParam param);
	
	void modularDelete(SoaIdsParam param);
	
	Pager<CfgRole> roles(Query query);
	
	int roleAdd(SoaSidParam param);
	
	void roleModify(NameIdParam param);
	
	void roleDelete(SoaIdParam param);
	
	void modularAuth(AuthParam param);
	
	void roleAuth(AuthParam param);
	
	void userAuth(AuthParam param);
	
	void auth(User user, CfgApi api);
}
