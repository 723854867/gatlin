package org.gatlin.soa.resource.service;

import java.util.List;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.manager.ResourceManager;
import org.gatlin.util.bean.enums.CacheUnit;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@javax.annotation.Resource
	private ResourceManager resourceManager;
	
	@Override
	public void upload(Resource resource) {
		_uploadCheck(resource);
		resourceManager.insert(resource);
	}
	
	@Override
	public void modify(ResourceModifyParam param) {
		resourceManager.modify(param);
	}
	
	@Override
	public Resource delete(long id) {
		return resourceManager.delete(id);
	}
	
	private void _uploadCheck(Resource resource) {
		CfgResource cfgResource = resourceManager.cfgResource(resource.getCfgId());
		Assert.notNull(ResourceCode.RESOURCE_CONFIG_NOT_EXIST, cfgResource);
		if (0 < cfgResource.getMaximum()) {
			Query query = new Query().eq("cfg_id", resource.getCfgId()).eq("owner", resource.getOwner());
			List<Resource> resources = resourceManager.resources(query);
			Assert.isTrue(ResourceCode.RESOURCE_COUNT_LIMIT, resources.size() < cfgResource.getMaximum());
		}
		if (0 < cfgResource.getCacheSize()) {
			CacheUnit unit = CacheUnit.valueOf(cfgResource.getCacheUnit());
			long maximumSize = unit.bytes(cfgResource.getCacheSize());
			Assert.isTrue(ResourceCode.RESOURCE_SIZE_LIMIT, maximumSize >= resource.getBytes());
		}
	}
	
	@Override
	public Pager<Resource> resources(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		List<Resource> resources = resourceManager.resources(query);
		return new Pager<Resource>(resources);
	}
}
