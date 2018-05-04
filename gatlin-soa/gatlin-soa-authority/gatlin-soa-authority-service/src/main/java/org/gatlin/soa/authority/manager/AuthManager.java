package org.gatlin.soa.authority.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.authority.bean.AuthCode;
import org.gatlin.soa.authority.bean.EntityGenerator;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.entity.CfgModular;
import org.gatlin.soa.authority.bean.entity.CfgRole;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ApiModifyParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.soa.authority.bean.param.NameIdParam;
import org.gatlin.soa.authority.mybatis.dao.AuthMappingDao;
import org.gatlin.soa.authority.mybatis.dao.CfgApiDao;
import org.gatlin.soa.authority.mybatis.dao.CfgModularDao;
import org.gatlin.soa.authority.mybatis.dao.CfgRoleDao;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthManager {

	@Resource
	private CfgApiDao cfgApiDao;
	@Resource
	private CfgRoleDao cfgRoleDao;
	@Resource
	private CfgModularDao cfgModularDao;
	@Resource
	private AuthMappingDao authMappingDao;
	
	public int apiAdd(ApiAddParam param) {
		CfgApi api = EntityGenerator.newCfgApi(param);
		cfgApiDao.insert(api);
		return api.getId();
	}
	
	public void apiModify(ApiModifyParam param) {
		CfgApi api = cfgApiDao.getByKey(param.getId());
		Assert.notNull(AuthCode.API_NOT_EXIST, api);
		api.setDesc(param.getDesc());
		api.setLogin(param.isLogin());
		api.setSerial(param.isSerial());
		api.setDeviceMod(param.getDeviceMod());
		api.setLockTimeout(param.getLockTimeout());
		api.setSecurityLevel(param.getSecurityLevel());
		api.setStorageType(param.getStorageType().name());
		api.setUpdated(DateUtil.current());
		cfgApiDao.update(api);
	}
	
	@Transactional
	public void apiDelete(SoaIdParam param) {
		long deleted = cfgApiDao.deleteByKey(param.getId());
		Assert.isTrue(AuthCode.API_NOT_EXIST, 1 == deleted);
		authMappingDao.deleteApi(param.getId());
	}
	
	@Transactional
	public int modularAdd(ModularAddParam param) {
		CfgModular parent = null == param.getParent() ? null : cfgModularDao.getByKey(param.getParent());
		if (null != param.getParent())
			Assert.notNull(AuthCode.MODULAR_NOT_EXIST, parent);
		if (null != parent) 
			_lockTrunk(parent.getTrunk());
		CfgModular modular = EntityGenerator.newCfgModular(param, parent);
		if (null != parent) {
			cfgModularDao.updateInsertLeft(parent.getTrunk(), parent.getRight());
			cfgModularDao.updateInsertRight(parent.getTrunk(), parent.getRight());
		} 
		cfgModularDao.insert(modular);
		return modular.getId();
	}
	
	public void modularModify(NameIdParam param) {
		CfgModular modular = cfgModularDao.getByKey(param.getId());
		Assert.notNull(AuthCode.MODULAR_NOT_EXIST, modular);
		modular.setName(param.getName());
		modular.setUpdated(DateUtil.current());
		cfgModularDao.update(modular);
	}
	
	@Transactional
	public Set<Integer> modularDelete(SoaIdParam param) {
		CfgModular modular = cfgModularDao.getByKey(param.getId());
		Assert.notNull(AuthCode.MODULAR_NOT_EXIST, modular);
		_lockTrunk(modular.getTrunk());
		int width = modular.getRight() - modular.getLeft() + 1;
		Set<Integer> deleted = new HashSet<Integer>();
		deleted.add(modular.getId());
		if (width == 2) {
			cfgModularDao.deleteByKey(modular.getId());
		} else {
			List<CfgModular> modulars = cfgModularDao.children(modular);
			modulars.forEach(item -> deleted.add(item.getId()));
			cfgModularDao.deleteByKeys(deleted);
		}
		cfgModularDao.updateDeleteLeft(modular.getTrunk(), modular.getRight(), width);
		cfgModularDao.updateDeleteRight(modular.getTrunk(), modular.getRight(), width);
		authMappingDao.deleteModulars(deleted);
		return deleted;
	}
	
	private void _lockTrunk(String trunk) {
		Query query = new Query().eq("trunk", trunk).forUpdate();
		cfgModularDao.queryList(query);
	}
	
	public int roleAdd(SoaSidParam param) {
		CfgRole role = EntityGenerator.newCfgRole(param.getId());
		cfgRoleDao.insert(role);
		return role.getId();
	}
	
	public void roleModify(NameIdParam param) {
		CfgRole role = cfgRoleDao.getByKey(param.getId());
		Assert.notNull(AuthCode.ROLE_NOT_EXIST, role);
		role.setName(param.getName());
		role.setUpdated(DateUtil.current());
		cfgRoleDao.insert(role);
	}
	
	@Transactional
	public void roleDelete(SoaIdParam param) {
		long deleted = cfgRoleDao.deleteByKey(param.getId());
		Assert.isTrue(AuthCode.ROLE_NOT_EXIST, 1 == deleted);
		authMappingDao.deleteRole(param.getId());
	}
	
	public CfgApi api(Query query) {
		return cfgApiDao.queryUnique(query);
	}
	
	public List<CfgApi> apis(Query query) {
		return cfgApiDao.queryList(query);
	}
	
	public List<CfgRole> roles(Query query) {
		return cfgRoleDao.queryList(query);
	}
}
