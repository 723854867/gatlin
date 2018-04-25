package org.gatlin.soa.resource.service;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.Entity;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.EntityGenerator;
import org.gatlin.soa.resource.bean.entity.CfgResourceRefer;
import org.gatlin.soa.resource.bean.entity.PubResource;
import org.gatlin.soa.resource.bean.enums.UploadType;
import org.gatlin.soa.resource.mybatis.dao.CfgResourceReferDao;
import org.gatlin.soa.resource.mybatis.dao.PubResourceDao;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@Resource
	private PubResourceDao pubResourceDao;
	@Resource
	private CfgResourceReferDao cfgResourceReferDao;
	
	@Override
	public void uploadBanner(String image, String link, int priority) {
		Query query = new Query().eq("upload_type", UploadType.BANNER.mark());
		CfgResourceRefer refer = cfgResourceReferDao.queryUnique(query);
		Assert.notNull(CoreCode.SYS_CONFIG_ERR, refer);
		int resourceId = refer.getResourceId();
		PubResource resource = EntityGenerator.newPubResource(image, UploadType.BANNER.mark(), resourceId, priority, link);
		pubResourceDao.insert(resource);
	}
}
