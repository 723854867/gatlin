package org.gatlin.soa.sinapay.api;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.notice.BidNotice;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;
import org.gatlin.soa.sinapay.bean.model.BidInfo;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.sinapay.bean.param.WithdrawParam;

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
	
	void bidCreate(BidInfo info);
	
	SinaBid bidNotice(BidNotice notice);
	
	void loanout(String ip, BidPurpose purpose, Object bizId, BigDecimal amount);
	
	void loanoutNotice(WithdrawNotice notice);
}
