package org.gatlin.soa.sinapay.service;

import javax.annotation.Resource;

import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
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
	public String withdrawPay(WithdrawParam param) {
		return sinaOrderManager.withdrawPay(param);
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
}
