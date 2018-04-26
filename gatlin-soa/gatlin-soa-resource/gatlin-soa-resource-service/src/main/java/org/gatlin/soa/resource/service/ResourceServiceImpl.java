package org.gatlin.soa.resource.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.CfgResourceMapping;
import org.gatlin.soa.resource.bean.entity.PubResource;
import org.gatlin.soa.resource.manager.ResourceManager;
import org.gatlin.soa.resource.mybatis.dao.CfgResourceDao;
import org.gatlin.soa.resource.mybatis.dao.CfgResourceMappingDao;
import org.gatlin.soa.resource.mybatis.dao.PubResourceDao;
import org.gatlin.util.bean.enums.CacheUnit;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@Resource
	private PubResourceDao pubResourceDao;
	@Resource
	private CfgResourceDao cfgResourceDao;
	@Resource
	private ResourceManager resourceManager;
	@Resource
	private CfgResourceMappingDao cfgResourceMappingDao;
	
	@Override
	public CfgResource uploadVerify(int type, int resourceId, long size, long major) {
		return uploadVerify(0, type, resourceId, size, major);
	}
	
	@Override
	public CfgResource uploadVerify(long owner, int type, int resourceId, long size, long major) {
		CfgResourceMapping upload = cfgResourceMappingDao.getByUploadTypeAndResourceId(type, resourceId);
		Assert.notNull(ResourceCode.RESOURCE_CONFIG_NOT_EXIST, upload);
		CfgResource resource = cfgResourceDao.getByKey(resourceId);
		Assert.notNull(CoreCode.SYS_CONFIG_ERR, resource);
		return _uploadVerify(owner, type, resource, size, major);
	}
	
	private CfgResource _uploadVerify(long owner, int type, CfgResource resource, long size, long major) {
		CacheUnit unit = CacheUnit.valueOf(resource.getCacheUnit());
		long bytes = unit.bytes(resource.getCacheSize());
		Assert.isTrue(ResourceCode.RESOURCE_SIZE_MAXIMUM, size <= bytes);
		int maximum = resource.getMaximumNum();
		if (0 >= maximum)
			return resource;
		Query query = new Query().eq("owner", owner).eq("upload_type", type).eq("resource_id", resource.getId());
		if (0 != resource.getMajor()) 
			query.eq("major", major);
		List<PubResource> resources = pubResourceDao.queryList(query);
		Assert.isTrue(ResourceCode.RESOURCE_COUNT_MAXIMUM, resources.size() < maximum);
		return resource;
	}
	
	@Override
	public PubResource upload(String url, String path, CfgResource resource, int type, int priority, long major) {
		return upload(0, url, path, resource, type, priority, major);
	}
	
	@Override
	public PubResource upload(long owner, String url, String path, CfgResource resource, int type, int priority, long major) {
		return resourceManager.upload(owner, url, path, resource, type, priority, major);
	}
	
	@Override
	public List<PubResource> unload(long resourceId) {
		return resourceManager.unload(resourceId);
	}
}
