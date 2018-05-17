package org.gatlin.soa.alipay.api;

import org.gatlin.soa.account.bean.entity.UserRecharge;

public interface AlipayAccountService {

	String recharge(UserRecharge recharge);
}
