package org.gatlin.soa.alipay.service;

import javax.annotation.Resource;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.alipay.api.AlipayAccountService;
import org.gatlin.soa.alipay.manager.AlipayAccountManager;
import org.springframework.stereotype.Service;

@Service("alipayAccountService")
public class AlipayAccountServiceImpl implements AlipayAccountService {
	
	@Resource
	private AlipayAccountManager alipayAccountManager;

	@Override
	public String recharge(Recharge recharge) {
		return alipayAccountManager.recharge(recharge);
	}
}
