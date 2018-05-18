package org.gatlin.soa.sinapay.api;

import org.gatlin.soa.account.bean.entity.Recharge;

public interface SinapayOrderService {

	// 托管充值
	String depositRecharge(Recharge recharge);
}
