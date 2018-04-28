package org.gatlin.soa.resource.manager;

import java.util.List;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.mybatis.dao.CfgResourceDao;
import org.gatlin.soa.resource.mybatis.dao.ResourceDao;
import org.gatlin.soa.resource.mybatis.dao.ResourceRouteDao;
import org.gatlin.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ResourceManager {
	
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private CfgResourceDao cfgResourceDao;
	@javax.annotation.Resource
	private ResourceRouteDao resourceRouteDao;
	
	public void insert(Resource resource) {
		resourceDao.insert(resource);
	}
	
	public void modify(ResourceModifyParam param) {
		Resource resource = resourceDao.getByKey(param.getId());
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		resource.setName(param.getName());
		resource.setPriority(param.getPriority());
		resource.setUpdated(DateUtil.current());
		resourceDao.update(resource);
	}
	
	@Transactional
	public Resource delete(long id) {
		Resource resource = resourceDao.getByKey(id);
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		resourceDao.deleteByKey(id);
		resourceRouteDao.deleteByResourceId(id);
		return resource;
	}
	
	public CfgResource cfgResource(int id) {
		return cfgResourceDao.getByKey(id);
	}
	
	public List<Resource> resources(Query query) {
		return resourceDao.queryList(query);
	}
}
