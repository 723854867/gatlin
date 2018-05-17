package org.gatlin.soa.sinapay.service;

import javax.annotation.Resource;

import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.manager.SinaOrderManager;
import org.springframework.stereotype.Service;

@Service("sinapayOrderService")
public class SinapayOrderServiceImpl implements SinapayOrderService {
	
	@Resource
	private SinaOrderManager sinaOrderManager;

	@Override
	public String depositRecharge(UserRecharge recharge) {
		return sinaOrderManager.depositRecharge(recharge);
	}
}
