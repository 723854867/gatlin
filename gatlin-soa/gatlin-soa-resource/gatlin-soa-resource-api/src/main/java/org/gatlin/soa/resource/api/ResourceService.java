package org.gatlin.soa.resource.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;

public interface ResourceService {
	
	void upload(Resource resource);
	
	void modify(ResourceModifyParam param);
	
	Resource delete(long id);
	
	Pager<Resource> resources(Query query);
}
