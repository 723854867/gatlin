package org.gatlin.soa.resource.bean;

import org.gatlin.core.bean.model.code.Code;

public interface ResourceCode {
	
	final Code RESOURCE_NOT_EXIST				= new Code("code.resource.not.exist", "资源不存在");
	final Code RESOURCE_CONFIG_NOT_EXIST		= new Code("code.resource.config.not.exist", "资源配置不存在");
	final Code RESOURCE_SIZE_LIMIT 				= new Code("code.resource.size.limit", "资源大小限制");
	final Code RESOURCE_COUNT_LIMIT 			= new Code("code.resource.count.limit", "资源数量限制");
	final Code PICTURE_NOT_EXIST				= new Code("code.resource.picture.not.exist", "图片资源不存在");
	final Code CONFG_UPLOAD_NOT_EXIST			= new Code("code.resource.config.upload.not.exist", "上传配置不存在");
}
