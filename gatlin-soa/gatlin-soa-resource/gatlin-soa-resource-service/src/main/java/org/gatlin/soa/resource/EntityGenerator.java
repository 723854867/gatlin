package org.gatlin.soa.resource;

import org.gatlin.soa.resource.bean.entity.ResourceRoute;
import org.gatlin.util.DateUtil;

public class EntityGenerator {

	public static final ResourceRoute newResourceRoute(String resourceId, String link) {
		ResourceRoute instance = new ResourceRoute();
		instance.setResourceId(resourceId);
		instance.setLink(link);
		instance.setCreated(DateUtil.current());
		return instance;
	}
}
