package org.gatlin.soa.sinapay.api;

import org.gatlin.soa.account.bean.entity.UserRecharge;

public interface SinapayOrderService {

	// 托管充值
	String depositRecharge(UserRecharge recharge);
}
