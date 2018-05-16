package org.gatlin.soa.resource.api;

import java.util.List;
import java.util.Set;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.model.ResourceInfo;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;

public interface ResourceService {
	
	List<CfgResource> configs(Query query);
	
	CfgResource uploadVerify(int cfgId, String owner, long bytes);
	
	Resource upload(Resource resource);
	
	Resource modify(ResourceModifyParam param);
	
	Set<Resource> delete(String id);
	
	ResourceInfo resource(Query query);
	
	Pager<ResourceInfo> resources(Query query);
}
