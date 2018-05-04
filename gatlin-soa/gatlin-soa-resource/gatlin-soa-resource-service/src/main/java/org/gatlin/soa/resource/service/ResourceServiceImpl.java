package org.gatlin.soa.resource.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.entity.ResourceRoute;
import org.gatlin.soa.resource.bean.model.ResourceInfo;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.manager.ResourceManager;
import org.gatlin.util.bean.enums.CacheUnit;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@javax.annotation.Resource
	private ResourceManager resourceManager;
	
	@Override
	public CfgResource uploadVerify(int cfgId, long owner, long bytes) {
		CfgResource cfgResource = resourceManager.cfgResource(cfgId);
		Assert.notNull(ResourceCode.RESOURCE_CONFIG_NOT_EXIST, cfgResource);
		if (0 < cfgResource.getMaximum()) {
			Query query = new Query().eq("cfg_id", cfgId).eq("owner", owner);
			List<Resource> resources = resourceManager.resources(query);
			Assert.isTrue(ResourceCode.RESOURCE_COUNT_LIMIT, resources.size() < cfgResource.getMaximum());
		}
		if (0 < cfgResource.getCacheSize()) {
			CacheUnit unit = CacheUnit.valueOf(cfgResource.getCacheUnit());
			long maximumSize = unit.bytes(cfgResource.getCacheSize());
			Assert.isTrue(ResourceCode.RESOURCE_SIZE_LIMIT, maximumSize >= bytes);
		}
		return cfgResource;
	}
	
	@Override
	public void upload(Resource resource) {
		resourceManager.insert(resource);
	}
	
	@Override
	public void modify(ResourceModifyParam param) {
		resourceManager.modify(param);
	}
	
	@Override
	public void link(String id, String link) {
		resourceManager.link(id, link);
	}
	
	@Override
	public Resource delete(String id) {
		return resourceManager.delete(id);
	}
	
	@Override
	public ResourceInfo resource(Query query) {
		Resource resource = resourceManager.resource(query);
		if (null == resource)
			return null;
		query = new Query().eq("resource_id", resource.getId());
		return new ResourceInfo(resource, resourceManager.resourceRoute(query));
	}
	
	@Override
	public Pager<ResourceInfo> resources(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		List<Resource> resources = resourceManager.resources(query);
		if (CollectionUtil.isEmpty(resources))
			return Pager.<ResourceInfo>empty();
		Set<String> set = new HashSet<String>();
		resources.forEach(item -> set.add(item.getId()));
		Map<String, ResourceRoute> routes = resourceManager.resourceRoutes(new Query().in("resource_id", set));
		return Pager.<ResourceInfo, Resource>convert(resources, list -> {
			List<ResourceInfo> infos = new ArrayList<ResourceInfo>();
			list.forEach(item -> infos.add(new ResourceInfo(item, routes.get(item.getId()))));
			return infos;
		});
	}
}
