package org.gatlin.soa.resource.api;

import java.util.Set;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.CfgResourceEditParam;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;

public interface ResourceService {
	
	Pager<CfgResource> configs(Query query);
	
	void cfgResourceEdit(CfgResourceEditParam param);
	
	CfgResource uploadVerify(int cfgId, String owner, long bytes);
	
	Resource upload(Resource resource, boolean replace);
	
	Resource modify(ResourceModifyParam param);
	
	Set<Resource> delete(String id, User user);
	
	ResourceInfo resource(Query query);
	
	Pager<ResourceInfo> resources(Query query);
}
