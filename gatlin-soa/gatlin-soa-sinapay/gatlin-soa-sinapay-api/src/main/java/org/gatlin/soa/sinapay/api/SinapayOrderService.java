package org.gatlin.soa.sinapay.api;

import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;

public interface SinapayOrderService {

	// 托管充值
	String depositRecharge(Recharge recharge, RechargeParam param);
	
	SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice);
	
	SinaCollect rechargeCollect(String id, DepositRechargeNotice notice);
	
	SinaCollect collectNotice(TradeNotice notice);
	
	String withdrawPay(WithdrawParam param);
	
	void withdrawPayNotice(TradeNotice notice);
	
	String withdraw(SoaSidParam param);
	
	SinaWithdraw withdrawNotice(WithdrawNotice notice);
	
	void withdrawCollect(String id, WithdrawNotice notice);
}
