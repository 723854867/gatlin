package org.gatlin.soa.sinapay.service;

import javax.annotation.Resource;

import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.param.SinaRechargeParam;
import org.gatlin.soa.sinapay.manager.SinaOrderManager;
import org.springframework.stereotype.Service;

@Service("sinapayOrderService")
public class SinapayOrderServiceImpl implements SinapayOrderService {
	
	@Resource
	private SinaOrderManager sinaOrderManager;

	@Override
	public String depositRecharge(Recharge recharge, SinaRechargeParam param) {
		return sinaOrderManager.depositRecharge(recharge, param);
	}
	
	@Override
	public void noticeDepositRecharge(DepositRechargeNotice notice) {
		
	}
}
