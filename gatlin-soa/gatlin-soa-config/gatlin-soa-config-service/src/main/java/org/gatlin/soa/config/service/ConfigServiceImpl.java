package org.gatlin.soa.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.config.EntityGenerator;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.entity.CfgGlobal;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.param.BankParam;
import org.gatlin.soa.config.bean.param.CfgGlobalParam;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.manager.DistrictManager;
import org.gatlin.soa.config.mybatis.dao.CfgBankDao;
import org.gatlin.soa.config.mybatis.dao.CfgGlobalDao;
import org.gatlin.util.DateUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	
	@Resource
	private CfgBankDao cfgBankDao;
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
	public CfgBank bank(String id) {
		return cfgBankDao.getByKey(id);
	}
	
	@Override
	public void bankEdit(BankParam param) {
		CfgBank bank = cfgBankDao.getByKey(param.getId());
		if (null == bank) 
			cfgBankDao.insert(EntityGenerator.newCfgBank(param));
		else {
			bank.setName(param.getName());
			bank.setValid(param.isValid());
			bank.setUpdated(DateUtil.current());
			cfgBankDao.update(bank);
		}
	}
	
	@Override
	public Pager<CfgBank> banks(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<CfgBank>(cfgBankDao.queryList(query));
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
