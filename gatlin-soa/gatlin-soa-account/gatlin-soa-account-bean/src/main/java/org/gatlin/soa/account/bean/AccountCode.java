package org.gatlin.soa.account.bean;

import org.gatlin.core.bean.model.code.Code;

public interface AccountCode {
	
	final Code USABLE_LACK				 					= new Code("code.account.usable.lack", "账户可用余额不足");
	final Code FROZEN_LACK				 					= new Code("code.account.frozen.lack", "账户冻结余额不足");
	final Code FROZEN_AMOUNT_ERR				 			= new Code("code.account.frozen.amount.err", "解冻金额错误");
	final Code RECHARGE_NOT_EXIST		 					= new Code("code.account.recharge.not.exist", "充值订单不存在");
	final Code RECHARGE_STATE_ERR 							= new Code("code.account.recharge.state.err", "充值订单状态错误");
	final Code RECHARGE_EXPIRY_TIME_ERR 					= new Code("code.account.recharge.timeout.err", "充值过期时间配置错误");
}
