package org.gatlin.soa.authority.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.authority.bean.AuthCode;
import org.gatlin.soa.authority.bean.EntityGenerator;
import org.gatlin.soa.authority.bean.entity.AuthMapping;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.entity.CfgModular;
import org.gatlin.soa.authority.bean.entity.CfgRole;
import org.gatlin.soa.authority.bean.enums.AuthMappingType;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ApiModifyParam;
import org.gatlin.soa.authority.bean.param.AuthParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.soa.authority.bean.param.ModularModifyParam;
import org.gatlin.soa.authority.bean.param.NameIdParam;
import org.gatlin.soa.authority.mybatis.dao.AuthMappingDao;
import org.gatlin.soa.authority.mybatis.dao.CfgApiDao;
import org.gatlin.soa.authority.mybatis.dao.CfgModularDao;
import org.gatlin.soa.authority.mybatis.dao.CfgRoleDao;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.soa.bean.param.SoaIdsParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.CollectionUtil;
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
		CfgModular modular = EntityGenerator.newCfgModular(param, parent);
		cfgModularDao.insert(modular);
		return modular.getId();
	}
	
	public void modularModify(ModularModifyParam param) {
		CfgModular modular = cfgModularDao.getByKey(param.getId());
		Assert.notNull(AuthCode.MODULAR_NOT_EXIST, modular);
		if (null != param.getCss())
			modular.setCss(param.getCss());
		modular.setUrl(param.getUrl());
		modular.setName(param.getName());
		modular.setPriority(param.getPriority());
		modular.setUpdated(DateUtil.current());
		cfgModularDao.update(modular);
	}
	
	@Transactional
	public void modularDelete(SoaIdsParam param) {
		cfgModularDao.deleteByKeys(param.getIds());
		authMappingDao.deleteModulars(param.getIds());
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
	
	public void modularAuth(AuthParam param) {
		CfgApi api = cfgApiDao.getByKey((int) param.getTid());
		Assert.notNull(AuthCode.API_NOT_EXIST, api);
		Assert.isTrue(AuthCode.UNLOGIN_API_NO_AUTH, api.isLogin());
		CfgModular modular = cfgModularDao.getByKey((int) param.getSid());
		Assert.notNull(AuthCode.MODULAR_NOT_EXIST, modular);
		authMappingDao.insert(EntityGenerator.newAuthMapping(AuthMappingType.MODULAR_API, param.getSid(), param.getTid()));
	}
	
	public void roleAuth(AuthParam param) {
		CfgRole role = cfgRoleDao.getByKey((int) param.getSid());
		Assert.notNull(AuthCode.ROLE_NOT_EXIST, role);
		CfgModular modular = cfgModularDao.getByKey((int) param.getTid());
		Assert.notNull(AuthCode.MODULAR_NOT_EXIST, modular);
		authMappingDao.insert(EntityGenerator.newAuthMapping(AuthMappingType.ROLE_MODULAR, param.getSid(), param.getTid()));
	}
	
	public void userAuth(AuthParam param) {
		CfgRole role = cfgRoleDao.getByKey((int) param.getTid());
		Assert.notNull(AuthCode.ROLE_NOT_EXIST, role);
		authMappingDao.insert(EntityGenerator.newAuthMapping(AuthMappingType.USER_ROLE, param.getSid(), param.getTid()));
	}
	
	public void auth(User user, CfgApi api) {
		Set<Long> set = authMappingDao.sids(AuthMappingType.MODULAR_API.mark(), api.getId());
		if (CollectionUtil.isEmpty(set))
			return;
		List<CfgModular> modulars = userModulars(user.getId());
		Set<Long> has = new HashSet<Long>();
		modulars.forEach(item -> has.add(Long.valueOf(item.getId())));
		set.retainAll(has);
		Assert.isTrue(AuthCode.AUTH_FAIL, !CollectionUtil.isEmpty(set));
	}
	
	public List<CfgApi> modularApis(int modularId) {
		Set<Long> set = authMappingDao.tids(AuthMappingType.MODULAR_API.mark(), modularId);
		return CollectionUtil.isEmpty(set) ? CollectionUtil.emptyList() : cfgApiDao.queryList(new Query().in("id", set));
	}
	
	public List<CfgModular> userModulars(long uid) {
		Set<Long> set = authMappingDao.tids(AuthMappingType.USER_ROLE.mark(), uid);
		if (CollectionUtil.isEmpty(set))
			return CollectionUtil.emptyList();
		List<AuthMapping> l = authMappingDao.queryList(new Query().eq("type", AuthMappingType.ROLE_MODULAR.mark()).in("sid", set));
		if (CollectionUtil.isEmpty(l))
			return CollectionUtil.emptyList();
		set.clear();
		l.forEach(item -> set.add(item.getTid()));
		return cfgModularDao.queryList(new Query().in("id", set));
	}
	
	public List<CfgRole> userRoles(long uid) {
		Set<Long> set = authMappingDao.tids(AuthMappingType.USER_ROLE.mark(), uid);
		return CollectionUtil.isEmpty(set) ? CollectionUtil.emptyList() : cfgRoleDao.queryList(new Query().in("id", set));
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
	
	public List<CfgModular> modulars() {
		return cfgModularDao.getAllList();
	}
}
