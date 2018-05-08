package org.gatlin.soa.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.entity.CfgGlobal;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.mybatis.dao.CfgGlobalDao;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private CfgGlobalDao cfgGlobalDao;

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
	public List<CfgDistrict> districts(Query query) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void districtAdd(DistrictAddParam param) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void districtModify(DistrictModifyParam param) {
		// TODO Auto-generated method stub
		
	}
}
