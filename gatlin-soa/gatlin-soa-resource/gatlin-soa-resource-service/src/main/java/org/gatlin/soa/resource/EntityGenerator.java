package org.gatlin.soa.resource;

import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.PubResource;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final PubResource newPubResource(long owner, String url, String path, int type, CfgResource resource, int priority, long major) {
		PubResource instance = new PubResource();
		instance.setUrl(url);
		instance.setUploadType(type);
		instance.setResourceId(resource.getId());
		instance.setPriority(priority);
		instance.setOwner(owner);
		instance.setPath(path);
		instance.setLink(StringUtil.EMPTY);
		instance.setMajor(0 == major ? null : major);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
