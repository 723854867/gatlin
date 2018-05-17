package org.gatlin.soa.resource.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.CfgResourceEditParam;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.mybatis.EntityGenerator;
import org.gatlin.soa.resource.mybatis.dao.CfgResourceDao;
import org.gatlin.soa.resource.mybatis.dao.ResourceDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ResourceManager {
	
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private CfgResourceDao cfgResourceDao;
	
	public void cfgResourceEdit(CfgResourceEditParam param) {
		CfgResource resource = cfgResourceDao.getByKey(param.getId());
		if (null == resource)
			cfgResourceDao.insert(EntityGenerator.newCfgResource(param));
		else {
			resource.setType(param.getType());
			resource.setName(param.getName());
			resource.setMinimum(param.getMinimum());
			resource.setMaximum(param.getMaximum());
			resource.setDirectory(param.getDirectory());
			resource.setCacheSize(param.getCacheSize());
			resource.setCacheUnit(param.getCacheUnit().name());
			resource.setUpdated(DateUtil.current());
			cfgResourceDao.update(resource);
		}
	}
	
	@Transactional
	public Resource upload(Resource resource) {
		CfgResource cfgResource = cfgResource(resource.getCfgId());
		Resource deleted = null;
		if (cfgResource.getType() == 3) {			// 链接资源如果父资源有链接资源且不为文本资源则需要删除原链接资源
			Resource parent = resourceDao.getByKey(resource.getOwner());
			if (StringUtil.hasText(parent.getLink()))
				deleted = _modifyLink(parent.getId(), cfgResource.getId());
			parent.setLink(resource.getUrl());
			parent.setUpdated(DateUtil.current());
			resourceDao.update(parent);
		}
		resourceDao.insert(resource);
		return deleted;
	}
	
	public Resource modify(ResourceModifyParam param) {
		Resource resource = resourceDao.getByKey(param.getId());
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		if (StringUtil.hasText(param.getName()))
			resource.setName(param.getName());
		if (null != param.getPriority())
			resource.setPriority(param.getPriority());
		Resource deleted = null;
		if (StringUtil.hasText(param.getLink())) {
			CfgResource cfgResource = cfgResourceDao.getByKey(resource.getCfgId());
			Assert.isTrue(ResourceCode.RESOURCE_LIN_DUPLICATED, cfgResource.getType() != 3);
			cfgResource = cfgResourceDao.queryUnique(new Query().eq("type", 3));
			deleted = _modifyLink(resource.getId(), cfgResource.getId());
			resource.setLink(param.getLink());
		}
		resource.setUpdated(DateUtil.current());
		resourceDao.update(resource);
		return deleted;
	}
	
	private Resource _modifyLink(String owner, int cfgId) {
		Resource child = resourceDao.queryUnique(new Query().eq("cfg_id", cfgId).eq("owner", owner));
		if (null != child)
			resourceDao.deleteByKey(child.getId());
		return child;
	}
	
	@Transactional
	public Set<Resource> delete(String id) {
		Set<Resource> resources = new HashSet<Resource>();
		Resource resource = resourceDao.getByKey(id);
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		if (StringUtil.hasText(resource.getLink())) {
			CfgResource cfgResource =  cfgResourceDao.queryUnique(new Query().eq("type", 3));
			Resource deleted = _modifyLink(resource.getId(), cfgResource.getId());
			if (null != deleted)
				resources.add(deleted);
		}
		resourceDao.deleteByKey(id);
		return resources;
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
	
	public List<CfgResource> configs(Query query) {
		return cfgResourceDao.queryList(query);
	}
	
	public Map<Integer, CfgResource> cfgResources(Query query) {
		return cfgResourceDao.query(query);
	}
}
