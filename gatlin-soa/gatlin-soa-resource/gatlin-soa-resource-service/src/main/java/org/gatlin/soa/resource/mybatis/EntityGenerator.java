package org.gatlin.soa.resource.mybatis;

import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.param.CfgResourceEditParam;
import org.gatlin.util.DateUtil;

public class EntityGenerator {

	public static final CfgResource newCfgResource(CfgResourceEditParam param) {
		CfgResource instance = new CfgResource();
		instance.setId(param.getId());
		instance.setType(param.getType());
		instance.setName(param.getName());
		instance.setMinimum(param.getMinimum());
		instance.setMaximum(param.getMaximum());
		instance.setDirectory(param.getDirectory());
		instance.setCacheSize(param.getCacheSize());
		instance.setCacheUnit(param.getCacheUnit());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
