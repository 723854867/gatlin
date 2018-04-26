package org.gatlin.soa.resource.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.EntityGenerator;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.PubResource;
import org.gatlin.soa.resource.mybatis.dao.CfgResourceDao;
import org.gatlin.soa.resource.mybatis.dao.PubResourceDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ResourceManager {
	
	@Resource
	private CfgResourceDao cfgResourceDao;
	@Resource
	private PubResourceDao pubResourceDao;

	@Transactional
	public PubResource upload(long owner, String url, String path, CfgResource resource, int type, int priority, long major) {
		if (0 != resource.getMajor()) {
			PubResource mr = pubResourceDao.getByKey(major);
			Assert.notNull(ResourceCode.RESOURCE_MAJOR_NOT_EXIST, mr);
			CfgResource cmr = cfgResourceDao.getByKey(mr.getResourceId());
			Assert.isTrue(CoreCode.FORBID, cmr.getId() == resource.getMajor());
			mr.setLink(url);
			mr.setUpdated(DateUtil.current());
			pubResourceDao.update(mr);
		} else
			major = 0;
		PubResource pubResource = EntityGenerator.newPubResource(owner, url, path, type, resource, priority, major);
		pubResourceDao.insert(pubResource);
		return pubResource;
	}
	
	@Transactional
	public List<PubResource> unload(long resourceId) { 
		PubResource resource = pubResourceDao.getByKey(resourceId);
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, resource);
		pubResourceDao.deleteByKey(resourceId);
		List<PubResource> list = new ArrayList<PubResource>();
		if (null == resource.getMajor()) {
			// 如果是主资源需要判断是否有副资源
			PubResource vice = pubResourceDao.queryUnique(new Query().eq("major", resource.getId()));
			if (null != vice) {			// 副资源一起删除
				pubResourceDao.deleteByKey(vice.getId());
				list.add(vice);
			}
		} else {
			// 如果是副资源则主资源的跳转链接需要设置为空
			PubResource major = pubResourceDao.getByKey(resource.getMajor());
			major.setLink(StringUtil.EMPTY);
			major.setUpdated(DateUtil.current());
			pubResourceDao.update(major);
		}
		list.add(resource);
		return list;
	}
}
