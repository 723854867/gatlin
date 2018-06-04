package org.gatlin.soa.resource.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.CfgResourceEditParam;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.bean.param.ResourcesParam;

public interface ResourceService {
	
	CfgResource cfgResource(int id);
	
	Pager<CfgResource> configs(Query query);
	
	void cfgResourceEdit(CfgResourceEditParam param);
	
	CfgResource uploadVerify(CfgResource cfgResource, String owner, long bytes);
	
	Resource upload(Resource resource, boolean replace);
	
	Resource modify(ResourceModifyParam param);
	
	Set<Resource> delete(String id, User user);
	
	ResourceInfo resource(Query query);
	
	Pager<ResourceInfo> list(ResourcesParam param);
	
	Map<Integer, List<ResourceInfo>> cfgIdListMap(ResourcesParam param);
	
	Map<String, ResourceInfo> ownerMap(ResourcesParam param);
	
	Map<String, List<ResourceInfo>> ownerListMap(ResourcesParam param);
	
	boolean minCheck(Object owner, Set<Integer> cfgIds);
}
