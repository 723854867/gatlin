package org.gatlin.soa.resource.api;

import java.util.List;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.model.ResourceInfo;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;

public interface ResourceService {
	
	List<CfgResource> configs();
	
	CfgResource uploadVerify(int cfgId, long owner, long bytes);
	
	void upload(Resource resource);
	
	void modify(ResourceModifyParam param);
	
	Resource delete(String id);
	
	void link(String id, String link);
	
	ResourceInfo resource(Query query);
	
	Pager<ResourceInfo> resources(Query query);
}
