package org.gatlin.soa.config.mybatis.dao;

import org.apache.ibatis.annotations.Update;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.config.bean.entity.CfgDistrict;

public interface CfgDistrictDao extends DBDao<String, CfgDistrict> {

	@Update("update cfg_district set valid = 0")
	void updateAllToInvalid();
	
}
