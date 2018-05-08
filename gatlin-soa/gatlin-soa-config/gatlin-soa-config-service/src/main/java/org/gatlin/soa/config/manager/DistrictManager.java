package org.gatlin.soa.config.manager;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.config.bean.entity.CfgDistrict;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.config.mybatis.dao.CfgDistrictDao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class DistrictManager {

	@Resource
	private CfgDistrictDao cfgDistrictDao;
	
	public List<CfgDistrict> districts(Query query) {
		return cfgDistrictDao.queryList(query);
	}
	
	public void districtAdd(DistrictAddParam param) {
		
	}
	
	public void districtModify(DistrictModifyParam param) {
		// TODO Auto-generated method stub
		
	}
}
