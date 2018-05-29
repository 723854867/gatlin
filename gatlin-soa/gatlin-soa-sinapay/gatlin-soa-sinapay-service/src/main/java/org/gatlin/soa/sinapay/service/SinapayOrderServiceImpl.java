package org.gatlin.soa.sinapay.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gatlin.sdk.sinapay.notice.BidNotice;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.model.WithdrawContext;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;
import org.gatlin.soa.sinapay.bean.model.BidInfo;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.sinapay.manager.SinaOrderManager;
import org.springframework.stereotype.Service;

@Service("sinapayOrderService")
public class SinapayOrderServiceImpl implements SinapayOrderService {
	
	@Resource
	private SinaOrderManager sinaOrderManager;

	@Override
	public String depositRecharge(Recharge recharge, RechargeParam param) {
		return sinaOrderManager.depositRecharge(recharge, param);
	}
	
	@Override
	public SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice) {
		return sinaOrderManager.noticeDepositRecharge(notice);
	}
	
	@Override
	public SinaCollect rechargeCollect(String id, DepositRechargeNotice notice) {
		return sinaOrderManager.rechargeCollect(id, notice);
	}
	
	@Override
	public SinaCollect collectNotice(TradeNotice notice) {
		return sinaOrderManager.collectNotice(notice);
	}

	@Override
	public String withdrawPay(WithdrawParam param, WithdrawContext context) {
		return sinaOrderManager.withdrawPay(param, context);
	}
	
	@Override
	public void withdrawPayNotice(TradeNotice notice) {
		sinaOrderManager.withdrawPayNotice(notice);
	}
	
	@Override
	public String withdraw(SoaSidParam param) {
		return sinaOrderManager.withdraw(param);
	}
	
	@Override
	public SinaWithdraw withdrawNotice(WithdrawNotice notice) {
		return sinaOrderManager.withdrawNotice(notice);
	}
	
	@Override
	public void withdrawCollect(String id, WithdrawNotice notice) {
		sinaOrderManager.withdrawCollect(id, notice);
	}
	
	@Override
	public void bidCreate(BidInfo info) {
		sinaOrderManager.bidCreate(info);
	}
	
	@Override
	public SinaBid bidNotice(BidNotice notice) {
		return sinaOrderManager.bidNotice(notice);
	}
	
	@Override
	public void loanout(String ip, BidPurpose purpose, Object bizId, BigDecimal amount) {
		sinaOrderManager.loanout(ip, purpose, bizId, amount);
	}
	
	@Override
	public void loanoutNotice(WithdrawNotice notice) {
		sinaOrderManager.loanoutNotice(notice);
	}
}
