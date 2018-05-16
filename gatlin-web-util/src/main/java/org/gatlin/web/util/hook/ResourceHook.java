package org.gatlin.web.util.hook;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.model.ResourceInfo;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.bean.param.ResourceUploadParam;

public abstract class ResourceHook {
	
	@Resource
	private ResourceService resourceService;

	public void uploadVerify(CfgResource resource, ResourceUploadParam param) {
		switch (resource.getType()) {
		case 1:
			Assert.isTrue(CoreCode.PARAM_ERR, !StringUtil.hasText(param.getOwner()));
			break;
		case 2:
			Assert.notNull(UserCode.USER_NOT_EIXST, param.getUser());
			param.setOwner(String.valueOf(param.getUser().getId()));
			break;
		case 3:
			ResourceInfo parent = resourceService.resource(new Query().eq("id", param.getOwner()));
			Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, parent);
			Assert.isTrue(ResourceCode.RESOURCE_LIN_DUPLICATED, parent.getType() != 3);
			break;
		default:
			verify(resource, param);
			break;
		}
	}
	
	protected abstract void verify(CfgResource resource, ResourceUploadParam param);
}
