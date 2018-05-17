package org.gatlin.soa.user.bean;

import org.gatlin.core.bean.model.code.Code;

public interface UserCode {

	final Code INVITOR_NOT_EXIST 			= new Code("code.user.invitor.not.exist", "邀请人不存在");
	final Code USER_LOCK_FAIL 				= new Code("code.user.lock.fail", "用户正在操作中");
	final Code USERNAME_EXIST 				= new Code("code.user.username.exist", "用户名已经存在");
	final Code USERNAME_NOT_EXIST 			= new Code("code.user.username.not.exist", "用户名不存在");
	final Code LOGIN_PWD_ERROR 				= new Code("code.user.pwd.error", "密码错误");
	final Code USER_UNLOGIN 				= new Code("code.user.unlogin", "用户未登录");
	final Code USER_NOT_EIXST 				= new Code("code.user.not.exist", "用户不存在");
	final Code USER_ROLE_NOT_EIXST 			= new Code("code.user.role.not.exist", "用户没有该角色权限");
	final Code USER_ROLE_MAXIMUM 			= new Code("code.user.role.maximum", "用户角色数量达到上限");
	final Code DEVICE_UNSUPPORT 			= new Code("code.user.device.unsupport", "该接口暂不支持该设备");
	final Code INVALID_TOKEN	 			= new Code("code.user.invalid.token", "无效的token");
	final Code PWD_UNMODIFIED	 			= new Code("code.user.pwd.unmodified", "新密码不可以和旧密码相同");
	final Code USER_ADDRESS_NOT_EXIST	 	= new Code("code.user.address.not.exist", "用户地址不存在");
	final Code USER_ADDRESS_COUNT_LIMIT		= new Code("code.user.address.count.maximum", "用户地址数目已达上限");
	final Code BANK_CARD_ALREADY_BIND		= new Code("code.user.bank.card.alread.bind", "银行卡已绑定");
}
