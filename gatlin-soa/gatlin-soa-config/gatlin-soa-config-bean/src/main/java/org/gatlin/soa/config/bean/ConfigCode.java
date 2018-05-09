package org.gatlin.soa.config.bean;

import org.gatlin.core.bean.model.code.Code;

public interface ConfigCode {
	
	final Code CONFIG_NOT_EXIST				= new Code("code.config.not.exit", "系统配置不存在");
	final Code DISTRICT_NOT_EXIST 			= new Code("code.config.district.not.exit", "地区配置不存在");
}
