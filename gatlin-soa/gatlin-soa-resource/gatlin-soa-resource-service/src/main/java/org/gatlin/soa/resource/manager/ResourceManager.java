package org.gatlin.soa.resource.manager;

import java.util.List;
import java.util.Map;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.EntityGenerator;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.entity.ResourceRoute;
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
	public Resource delete(String id) {
		Resource resource = resourceDao.getByKey(id);
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		resourceDao.deleteByKey(id);
		resourceRouteDao.deleteByKey(id);
		return resource;
	}
	
	public void link(String id, String link) {
		Resource resource = resourceDao.getByKey(id);
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		ResourceRoute route = EntityGenerator.newResourceRoute(id, link);
		resourceRouteDao.replace(route);
	}
	
	public CfgResource cfgResource(int id) {
		return cfgResourceDao.getByKey(id);
	}
	
	public Resource resource(Query query) {
		return resourceDao.queryUnique(query);
	}
	
	public List<Resource> resources(Query query) {
		return resourceDao.queryList(query);
	}
	
	public List<CfgResource> configs() {
		return cfgResourceDao.getAllList();
	}
	
	public ResourceRoute resourceRoute(Query query) { 
		return resourceRouteDao.queryUnique(query);
	}
	
	public Map<String, ResourceRoute> resourceRoutes(Query query) { 
		return resourceRouteDao.query(query);
	}
}
