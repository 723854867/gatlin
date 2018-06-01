package org.gatlin.soa.resource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.CfgResourceEditParam;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.manager.ResourceManager;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@javax.annotation.Resource
	private ResourceManager resourceManager;
	
	@Override
	public CfgResource cfgResource(int id) {
		return resourceManager.cfgResource(id);
	}
	
	@Override
	public Pager<CfgResource> configs(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<CfgResource>(resourceManager.configs(query));
	}
	
	@Override
	public void cfgResourceEdit(CfgResourceEditParam param) {
		resourceManager.cfgResourceEdit(param);
	}
	
	@Override
	public CfgResource uploadVerify(CfgResource cfgResource, String owner, long bytes) {
		if (0 < cfgResource.getMaximum()) {
			Query query = new Query().eq("cfg_id", cfgResource.getId());
			if (StringUtil.hasText(owner))
				query.eq("owner", owner);
			List<Resource> resources = resourceManager.resources(query);
			Assert.isTrue(ResourceCode.RESOURCE_COUNT_LIMIT, resources.size() < cfgResource.getMaximum());
		}
		if (0 < cfgResource.getCacheSize()) {
			long maximumSize = cfgResource.getCacheUnit().bytes(cfgResource.getCacheSize());
			Assert.isTrue(ResourceCode.RESOURCE_SIZE_LIMIT, maximumSize >= bytes);
		}
		return cfgResource;
	}
	
	@Override
	public Resource upload(Resource resource, boolean replace) {
		return resourceManager.upload(resource, replace);
	}
	
	@Override
	public Resource modify(ResourceModifyParam param) {
		return resourceManager.modify(param);
	}
	
	@Override
	public Set<Resource> delete(String id, User user) {
		return resourceManager.delete(id, user);
	}
	
	@Override
	public ResourceInfo resource(Query query) {
		Resource resource = resourceManager.resource(query);
		if (null == resource)
			return null;
		CfgResource cfgResource = resourceManager.cfgResource(resource.getCfgId());
		return _resourceInfo(resource, cfgResource);
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
			resources.forEach(item -> infos.add(_resourceInfo(item, map.get(item.getCfgId()))));
			return infos;
		});
	}
	
	private ResourceInfo _resourceInfo(Resource resource, CfgResource cfgResource) {
		ResourceInfo info = new ResourceInfo();
		info.setType(cfgResource.getType());
		info.setId(resource.getId());
		info.setCfgId(resource.getCfgId());
		info.setUrl(resource.getUrl());
		info.setName(resource.getName());
		info.setLink(resource.getLink());
		info.setOwner(resource.getOwner());
		info.setPriority(resource.getPriority());
		info.setCreated(resource.getCreated());
		return info;
	}
	
	@Override
	public boolean minCheck(Object owner, Set<Integer> cfgIds) {
		Query query = new Query().in("id", cfgIds).gt("minimum", 0);
		Map<Integer, CfgResource> map = resourceManager.cfgResources(query);
		query = new Query().eq("owner", owner).in("cfg_id", cfgIds);
		List<Resource> resources = resourceManager.resources(query);
		Map<Integer, List<Resource>> m = new HashMap<Integer, List<Resource>>();
		resources.forEach(resource -> {
			List<Resource> l = m.get(resource.getCfgId());
			if (null == l) {
				l = new ArrayList<Resource>();
				m.put(resource.getCfgId(), l);
			}
			l.add(resource);
		});
		for (CfgResource resource : map.values()) {
			List<Resource> l = m.get(resource.getId());
			if (l.size() < resource.getMinimum())
				return false;
		}
		return true;
	}
}
