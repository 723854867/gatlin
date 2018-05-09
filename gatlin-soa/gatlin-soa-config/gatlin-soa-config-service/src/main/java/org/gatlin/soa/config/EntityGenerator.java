package org.gatlin.soa.config;

import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.util.DateUtil;

public class EntityGenerator {

	public static final CfgDistrict newCfgDistrict(DistrictAddParam param) {
		CfgDistrict instance = new CfgDistrict();
		instance.setCode(param.getCode());
		instance.setName(param.getName());
		instance.setLevel(param.getLevel().mark());
		instance.setAbname(param.getName());
		instance.setValid(true);
		instance.setParent(param.getParent());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
