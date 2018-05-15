package org.gatlin.soa.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.entity.CfgGlobal;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.param.CfgGlobalParam;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.manager.DistrictManager;
import org.gatlin.soa.config.mybatis.dao.CfgGlobalDao;
import org.gatlin.util.DateUtil;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private CfgGlobalDao cfgGlobalDao;
	@Resource
	private DistrictManager districtManager;

	@Override
	public <T> T config(Option<T> option) {
		CfgGlobal config = cfgGlobalDao.getByKey(option.key());
		T value = null == config ? option.defaultValue() : GatlinConfigration.CONVERSION_SERVICE.convert(config.getValue(), option.clazz());
		return Assert.notNull(ConfigCode.CONFIG_NOT_EXIST, value);
	}
	
	@Override
	public Configs configs(Query query) {
		Configs configs = new Configs();
		configs.setGlobals(cfgGlobalDao.query(query));
		return configs;
	}
	
	@Override
	public void configUpdate(CfgGlobalParam cfgGlobalParam) {
		CfgGlobal cfgGlobal = cfgGlobalDao.getByKey(cfgGlobalParam.getKey());
		Assert.notNull(ConfigCode.CONFIG_NOT_EXIST, cfgGlobal);
		if(null != cfgGlobalParam.getValue()) 
			cfgGlobal.setValue(cfgGlobalParam.getValue());
		cfgGlobal.setUpdated(DateUtil.current());
		cfgGlobalDao.update(cfgGlobal);
	}
	
	@Override
	public Geo geo(String code, boolean validCheck) {
		return districtManager.geo(code, validCheck);
	}
	
	@Override
	public List<CfgDistrict> districts(Query query) {
		return districtManager.districts(query);
	}
	
	@Override
	public void districtAdd(DistrictAddParam param) {
		districtManager.add(param);
	}
	
	@Override
	public void districtModify(DistrictModifyParam param) {
		districtManager.modify(param);
	}
}
