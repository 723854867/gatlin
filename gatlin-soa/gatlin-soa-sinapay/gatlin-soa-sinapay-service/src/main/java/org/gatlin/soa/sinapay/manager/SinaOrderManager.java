package org.gatlin.soa.sinapay.manager;

import javax.annotation.Resource;

import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaOrderManager {
	
	@Resource
	private AccountService accountService;

	@Transactional
	public String depositRecharge(UserRecharge recharge) {
		accountService.recharge(recharge);
		return null;
	}
}
