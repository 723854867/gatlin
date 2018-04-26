package org.gatlin.soa.resource.bean;

import org.gatlin.core.bean.model.code.Code;

public interface ResourceCode {
	
	final Code RESOURCE_NOT_EXIST				= new Code("code.resource.not.exist", "资源不存在");
	final Code RESOURCE_SIZE_MAXIMUM 			= new Code("code.resource.size.maximum", "资源大小限制");
	final Code RESOURCE_COUNT_MAXIMUM 			= new Code("code.resource.count.maximum", "资源数量限制");
	final Code RESOURCE_MAJOR_NOT_EXIST			= new Code("code.resource.major.not.exist", "主资源不存在");
	final Code RESOURCE_CONFIG_NOT_EXIST		= new Code("code.resource.config.not.exist", "资源配置不存在");
}
