package org.gatlin.soa.authority;

import org.gatlin.soa.authority.bean.entity.AuthMapping;
import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.entity.CfgModular;
import org.gatlin.soa.authority.bean.entity.CfgRole;
import org.gatlin.soa.authority.bean.enums.AuthMappingType;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.util.DateUtil;

public class EntityGenerator {
	
	public static final CfgApi newCfgApi(ApiAddParam param) {
		CfgApi instance = new CfgApi();
		instance.setPath(param.getPath());
		instance.setDesc(param.getDesc());
		instance.setLogin(param.isLogin());
		instance.setSerial(param.isSerial());
		instance.setDeviceMod(param.getDeviceMod());
		instance.setLockTimeout(param.getLockTimeout());
		instance.setSecurityLevel(param.getSecurityLevel());
		instance.setStorageType(param.getStorageType());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}

	public static final CfgModular newCfgModular(ModularAddParam param, CfgModular parent) {
		CfgModular instance = new CfgModular();
		instance.setCss(param.getCss());
		instance.setUrl(param.getUrl());
		instance.setName(param.getName());
		instance.setPriority(param.getPriority());
		instance.setParent(null == parent ? 0 : parent.getId());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final CfgRole newCfgRole(String name) {
		CfgRole instance = new CfgRole();
		instance.setName(name);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final AuthMapping newAuthMapping(AuthMappingType type, long sid, long tid) {
		AuthMapping instance = new AuthMapping();
		instance.setSid(sid);
		instance.setTid(tid);
		instance.setType(type);
		instance.setCreated(DateUtil.current());
		return instance;
	}
}
