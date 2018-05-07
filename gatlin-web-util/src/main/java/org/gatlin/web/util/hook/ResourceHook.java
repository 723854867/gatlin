package org.gatlin.web.util.hook;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.web.bean.param.ResourceUploadParam;

public abstract class ResourceHook {

	public void uploadVerify(CfgResource resource, ResourceUploadParam param) {
		switch (resource.getType()) {
		case 1:
			Assert.isTrue(CoreCode.PARAM_ERR, 0 == param.getOwner());
			break;
		case 2:
			Assert.notNull(UserCode.USER_NOT_EIXST, param.getUser());
			break;
		default:
			verify(resource, param);
			break;
		}
	}
	
	protected abstract void verify(CfgResource resource, ResourceUploadParam param);
}
