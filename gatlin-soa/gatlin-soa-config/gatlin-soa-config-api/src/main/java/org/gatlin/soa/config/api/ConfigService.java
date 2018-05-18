package org.gatlin.soa.config.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.param.BankParam;
import org.gatlin.soa.config.bean.param.CfgGlobalParam;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;

public interface ConfigService {
	
	Configs configs(Query query);
	
	<T> T config(Option<T> option);
	
	CfgBank bank(String id);
	
	void bankEdit(BankParam param);
	
	Pager<CfgBank> banks(Query query);
	
	Geo geo(String code, boolean validCheck);
	
	Map<String, Geo> geos(Set<String> codes, boolean validCheck);
	
	List<CfgDistrict> districts(Query query);
	
	void districtAdd(DistrictAddParam param);
	
	void districtModify(DistrictModifyParam param);

	void configUpdate(CfgGlobalParam cfgGlobalParam);
}
