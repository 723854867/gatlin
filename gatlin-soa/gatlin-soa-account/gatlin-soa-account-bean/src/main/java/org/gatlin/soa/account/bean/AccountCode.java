package org.gatlin.soa.account.bean;

import org.gatlin.core.bean.model.code.Code;

public interface AccountCode {
	
	final Code RECHARGE_NOT_EXIST		 					= new Code("code.account.recharge.not.exist", "充值订单不存在");
	final Code RECHARGE_EXPIRY_TIME_ERR 					= new Code("code.account.recharge.timeout.err", "充值过期时间配置错误");
}
