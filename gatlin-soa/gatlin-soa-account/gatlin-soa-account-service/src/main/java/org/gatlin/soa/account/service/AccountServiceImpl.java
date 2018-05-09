package org.gatlin.soa.account.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.entity.LogUserAccount;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.manager.AccountManager;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Resource
	private AccountManager accountManager;

	@Override
	public void init(long uid, int mod) {
		accountManager.init(uid, mod);
	}
	
	@Override
	public void process(LogUserAccount log) {
		accountManager.process(log);
	}
	
	@Override
	public UserAccount account(Query query) {
		return accountManager.account(query);
	}
	
	@Override
	public Pager<UserAccount> accounts(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<UserAccount>(accountManager.accounts(query));
	}
	
	@Override
	public Object recharge(UserRecharge recharge) {
		return accountManager.recharge(recharge);
	}
	
	@Override
	public void rechargeNotice(String id, RechargeState update) {
		accountManager.rechargeNotice(id, update);
	}
}
