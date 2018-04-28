package org.gatlin.soa.resource.mybatis.dao;

import org.apache.ibatis.annotations.Delete;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.resource.bean.entity.ResourceRoute;

public interface ResourceRouteDao extends DBDao<Long, ResourceRoute> {

	@Delete("DELETE FROM resource_route WHERE from=#{resourceId} OR to=#{resourceId}")
	int deleteByResourceId(long resourceId);
}
