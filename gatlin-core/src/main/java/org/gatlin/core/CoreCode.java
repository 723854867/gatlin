package org.gatlin.core;

import org.gatlin.core.bean.model.code.Code;

public interface CoreCode {
	
	final Code SUCCESS 				= new Code("code.success", "成功");
	final Code FORBID				= new Code("code.forbid", "非法访问");
	final Code PARAM_ERR			= new Code("code.param.err", "参数错误");
	final Code SYSTEM_ERR 			= new Code("code.system.err", "系统错误");
	final Code RESOURCE_LOCKED		= new Code("code.resource.locked", "资源正在被使用");
}
