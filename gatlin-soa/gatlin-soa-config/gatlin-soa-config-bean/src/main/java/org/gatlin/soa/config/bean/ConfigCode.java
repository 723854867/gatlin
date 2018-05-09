package org.gatlin.soa.config.bean;

import org.gatlin.core.bean.model.code.Code;

public interface ConfigCode {
	
	final Code CONFIG_NOT_EXIST 			= new Code("code.config.not.exist", "配置不存在");
	final Code MODULAR_NOT_EXIST 			= new Code("code.modular.not.exist", "模块不存在");
	final Code GATEWAY_NOT_EXIST 			= new Code("code.gateway.not.exist", "网关不存在");
	final Code DISTRICT_NOT_EXIST 			= new Code("code.config.district.not.exit", "地区配置不存在");
}
