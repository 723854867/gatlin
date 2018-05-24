package org.gatlin.soa.config.bean;

import org.gatlin.core.bean.model.code.Code;

public interface ConfigCode {
	
	final Code BANK_UNSUPPORT				= new Code("code.config.bank.unsupport", "暂不支持该银行");
	final Code CONFIG_NOT_EXIST				= new Code("code.config.not.exit", "系统配置不存在");
	final Code CONFIG_UNEDITABLE			= new Code("code.config.uneditable", "此系统配置不可编辑");
	final Code DISTRICT_NOT_EXIST 			= new Code("code.config.district.not.exit", "地区配置不存在");
}
