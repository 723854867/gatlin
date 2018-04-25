package org.gatlin.soa.resource.bean;

import org.gatlin.soa.resource.bean.entity.PubResource;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final PubResource newPubResource(String image, int type, int resourceId, int priority, String link) {
		PubResource instance = new PubResource();
		instance.setImage(image);
		instance.setUploadType(type);
		instance.setResourceId(resourceId);
		instance.setPriority(priority);
		instance.setLink(null == link ? StringUtil.EMPTY : link);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
