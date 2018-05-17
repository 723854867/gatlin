package org.gatlin.core;

import org.gatlin.core.bean.model.code.Code;

public interface CoreCode {
	
	final Code SUCCESS 								= new Code("code.success", "成功");
	final Code FORBID								= new Code("code.forbid", "非法访问");
	final Code PARAM_ERR							= new Code("code.param.err", "参数错误");
	final Code SYSTEM_ERR 							= new Code("code.system.err", "系统错误");
	final Code RESOURCE_LOCKED						= new Code("code.resource.locked", "资源正在被使用");
	final Code SDK_INVOKE_FAIL						= new Code("code.sdk.invoke.fail", "第三方调用失败");
	final Code DATA_STATE_CHANGED					= new Code("code.data.state.changed", "数据状态已改变");
	final Code IDENTITY_OR_MOBILE_DUPLICATED		= new Code("code.identity.or.mobile.duplicated", "身份证或者手机号已被使用");
}
