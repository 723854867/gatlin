package org.gatlin.soa.authority.bean;

import org.gatlin.soa.authority.bean.entity.CfgApi;
import org.gatlin.soa.authority.bean.entity.CfgModular;
import org.gatlin.soa.authority.bean.entity.CfgRole;
import org.gatlin.soa.authority.bean.param.ApiAddParam;
import org.gatlin.soa.authority.bean.param.ModularAddParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

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
		instance.setStorageType(param.getStorageType().name());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}

	public static final CfgModular newCfgModular(ModularAddParam param, CfgModular parent) {
		CfgModular instance = new CfgModular();
		instance.setUrl(param.getUrl());
		instance.setName(param.getName());
		instance.setPriority(param.getPriority());
		if (null == parent) {
			instance.setLeft(1);
			instance.setRight(2);
			instance.setLayer(1);
			instance.setTrunk(IDWorker.INSTANCE.nextSid());
		} else {
			instance.setTrunk(parent.getTrunk());
			instance.setLeft(parent.getRight());
			instance.setRight(parent.getLeft() + 1);
			instance.setLayer(parent.getLayer() + 1);
		}
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
}
