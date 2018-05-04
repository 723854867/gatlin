package org.gatlin.soa.authority.bean;

import org.gatlin.core.bean.model.code.Code;

public interface AuthCode {
	
	final Code API_NOT_EXIST 				= new Code("code.api.not.exist", "接口不存在");
	final Code ROLE_NOT_EXIST 				= new Code("code.role.not.exist", "角色不存在");
	final Code MODULAR_NOT_EXIST 			= new Code("code.modular.not.exist", "模块不存在");
}
