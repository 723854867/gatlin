package org.gatlin.soa.authority.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.authority.bean.entity.CfgApi;

public interface CfgApiDao extends DBDao<Integer, CfgApi> {

	@Select("SELECT * FROM cfg_api WHERE id IN(SELECT tid FROM auth_mapping WHERE `type`=1 AND sid=#{modularId})")
	List<CfgApi> modularApis(int modularId);
}
