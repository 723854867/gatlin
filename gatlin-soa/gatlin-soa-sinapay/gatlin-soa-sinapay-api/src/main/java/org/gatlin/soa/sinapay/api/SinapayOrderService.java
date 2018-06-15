package org.gatlin.soa.sinapay.api;

import org.gatlin.sdk.sinapay.notice.BidNotice;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaLoanout;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;
import org.gatlin.soa.sinapay.bean.model.BidInfo;
import org.gatlin.soa.sinapay.bean.param.WithdrawParam;

public interface SinapayOrderService {

	// 托管充值
	String depositRecharge(Recharge recharge, SoaParam param);
	
	void depositRechargeTimeout(String id);
	
	SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice);
	
	SinaCollect rechargeCollect(String id, String ip);
	
	SinaCollect collectNotice(TradeNotice notice);
	
	String withdrawPay(WithdrawParam param);
	
	void withdrawPayNotice(TradeNotice notice);
	
	String withdraw(SoaSidParam param);
	
	void withdrawTimeout(String id);
	
	SinaWithdraw withdrawNotice(WithdrawNotice notice);
	
	void withdrawFailure(String id, WithdrawNotice notice);
	
	SinaBid bid(BidPurpose purpose, String bizId);
	
	void bidCreate(BidInfo info);
	
	SinaBid bidNotice(BidNotice notice);
	
	void loanout(SinaLoanout loanout);
	
	void loanoutNotice(WithdrawNotice notice);
}
