package org.gatlin.web.util.validator;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.bean.param.ResourceUploadParam;

public abstract class ResourceHook {
	
	@Resource
	protected ConfigService configService;
	@Resource
	protected ResourceService resourceService;

	public void uploadVerify(CfgResource resource, ResourceUploadParam param) {
		switch (resource.getType()) {
		case 1:
			Assert.isTrue(CoreCode.PARAM_ERR, !StringUtil.hasText(param.getOwner()));
			param.setOwner(StringUtil.EMPTY);
			break;
		case 2:
			Assert.notNull(UserCode.USER_NOT_EIXST, param.getUser());
			param.setOwner(String.valueOf(param.getUser().getId()));
			break;
		case 3:
			Assert.hasText(CoreCode.PARAM_ERR, param.getOwner());
			ResourceInfo parent = resourceService.resource(new Query().eq("id", param.getOwner()));
			Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, parent);
			Assert.isTrue(ResourceCode.RESOURCE_LIN_DUPLICATED, parent.getType() != 3);
			break;
		case 4:
			Assert.hasText(CoreCode.PARAM_ERR, param.getOwner());
			CfgBank bank = configService.bank(param.getOwner());
			Assert.notNull(ConfigCode.BANK_UNSUPPORT, bank);
			break;
		default:
			verify(resource, param);
			break;
		}
	}
	
	protected abstract void verify(CfgResource resource, ResourceUploadParam param);
}
