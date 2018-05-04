package org.gatlin.soa.authority.mybatis;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.authority.bean.entity.AuthMapping;

public interface AuthMappingDao extends DBDao<Long, AuthMapping> {

	@Delete("DELETE FROM auth_mapping WHERE `type`=1 AND tid=#{apiId}")
	long deleteApi(int apiId);
	
	@Delete("DELETE FROM auth_mapping WHERE (`type`=3 AND tid=#{roleId}) OR (`type`=2 AND sid=#{roleId})")
	long deleteRole(int roleId);
	
	long deleteModulars(Collection<Integer> modulars);
}
