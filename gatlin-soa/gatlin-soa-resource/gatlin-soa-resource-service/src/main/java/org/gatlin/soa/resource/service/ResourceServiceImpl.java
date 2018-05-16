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
	public List<CfgResource> configs(Query query) {
		return resourceManager.configs(query);
	}
	
	@Override
	public CfgResource uploadVerify(int cfgId, String owner, long bytes) {
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
	public Resource upload(Resource resource) {
		return resourceManager.upload(resource);
	}
	
	@Override
	public Resource modify(ResourceModifyParam param) {
		return resourceManager.modify(param);
	}
	
	@Override
	public Set<Resource> delete(String id) {
		return resourceManager.delete(id);
	}
	
	@Override
	public ResourceInfo resource(Query query) {
		Resource resource = resourceManager.resource(query);
		if (null == resource)
			return null;
		CfgResource cfgResource = resourceManager.cfgResource(resource.getCfgId());
		return new ResourceInfo(resource, cfgResource);
	}
	
	@Override
	public Pager<ResourceInfo> resources(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		List<Resource> resources = resourceManager.resources(query);
		if (CollectionUtil.isEmpty(resources))
			return Pager.<ResourceInfo>empty();
		Set<Integer> set = new HashSet<Integer>();
		resources.forEach(item -> set.add(item.getCfgId()));
		Map<Integer, CfgResource> map = resourceManager.cfgResources(new Query().in("id", set));
		return Pager.<ResourceInfo, Resource>convert(resources, () -> {
			List<ResourceInfo> infos = new ArrayList<ResourceInfo>();
			resources.forEach(item -> infos.add(new ResourceInfo(item, map.get(item.getCfgId()))));
			return infos;
		});
	}
}
