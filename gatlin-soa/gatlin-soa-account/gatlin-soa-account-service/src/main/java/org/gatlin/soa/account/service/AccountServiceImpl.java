package org.gatlin.soa.account.service;

import javax.annotation.Resource;

import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.manager.AccountManager;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Resource
	private AccountManager accountManager;

	@Override
	public void init(long uid, int mod) {
		accountManager.init(uid, mod);
	}
}
