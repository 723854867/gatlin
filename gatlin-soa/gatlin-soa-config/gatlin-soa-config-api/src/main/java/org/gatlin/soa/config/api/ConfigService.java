package org.gatlin.soa.config.api;

import java.util.List;

import org.gatlin.core.bean.model.option.Option;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.param.CfgGlobalParam;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.bean.param.DistrictsParam;

public interface ConfigService {
	
	Configs configs(Query query);
	
	<T> T config(Option<T> option);
	
	Geo geo(String code, boolean validCheck);
	
	List<CfgDistrict> districts(Query query);
	
	void districtAdd(DistrictAddParam param);
	
	void districtModify(DistrictModifyParam param);

	void configUpdate(CfgGlobalParam cfgGlobalParam);

	void districtAuth(DistrictsParam param);
}
