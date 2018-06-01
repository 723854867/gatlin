package org.gatlin.soa.resource.mybatis.dao;

import java.util.List;

import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourcesParam;

public interface ResourceDao extends DBDao<String, Resource> {
	
	List<ResourceInfo> list(ResourcesParam param);
}
