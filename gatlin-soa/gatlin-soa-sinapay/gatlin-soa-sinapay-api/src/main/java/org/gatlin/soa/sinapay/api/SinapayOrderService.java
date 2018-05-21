package org.gatlin.soa.sinapay.api;

import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.param.SinaRechargeParam;

public interface SinapayOrderService {

	// 托管充值
	String depositRecharge(Recharge recharge, SinaRechargeParam param);
	
	SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice);
}
