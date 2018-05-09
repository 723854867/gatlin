package org.gatlin.soa.authority.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.authority.bean.entity.CfgModular;

public interface CfgModularDao extends DBDao<Integer, CfgModular> {
	
	@Select("SELECT * FROM cfg_modular WHERE id IN(SELECT tid FROM auth_mapping WHERE `type`=2 AND sid IN(SELECT tid FROM auth_mapping WHERE `type`=3 AND sid=#{uid}))")
	List<CfgModular> userModulars(long uid);
}
