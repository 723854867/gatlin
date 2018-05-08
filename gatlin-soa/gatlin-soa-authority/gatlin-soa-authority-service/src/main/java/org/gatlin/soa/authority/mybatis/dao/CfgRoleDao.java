package org.gatlin.soa.authority.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.authority.bean.entity.CfgRole;

public interface CfgRoleDao extends DBDao<Integer, CfgRole> {

	@Select("SELECT * FROM cfg_role WHERE id IN(SELECT tid FROM auth_mapping WHERE `type`=3 AND sid=#{uid})")
	List<CfgRole> userRoles(long uid);
}
