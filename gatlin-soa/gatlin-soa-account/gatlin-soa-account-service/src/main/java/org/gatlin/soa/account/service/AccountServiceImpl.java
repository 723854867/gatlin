package org.gatlin.soa.account.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.account.manager.AccountManager;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Resource
	private AccountManager accountManager;

	@Override
	public void userCreate(long uid, int mod) {
		accountManager.userCreate(uid, mod);
	}
	
	@Override
	public Account account(Query query) {
		return accountManager.account(query);
	}
	
	@Override
	public Account platAccount(AccountType type) {
		return accountManager.platAccount(type);
	}
	
	@Override
	public Account userAccount(long uid, AccountType type) {
		return accountManager.userAccount(uid, type);
	}
	
	@Override
	public Account companyAccount(int companyId, AccountType type) {
		return accountManager.companyAccount(companyId, type);
	}
	
	@Override
	public Pager<Account> accounts(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<Account>(accountManager.accounts(query));
	}
	
	@Override
	public void process(AccountDetail detail) {
		accountManager.process(detail);
	}
	
	@Override
	public void recharge(Recharge recharge) {
		accountManager.recharge(recharge);
	}
	
	@Override
	public void rechargeNotice(String id, RechargeState update) {
		accountManager.rechargeNotice(id, update);
	}
	
	@Override
	public Pager<Recharge> recharges(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<Recharge>(accountManager.recharges(query));
	}
	
	@Override
	public Withdraw withdraw(WithdrawParam param) {
		return accountManager.withdraw(param);
	}
	
	@Override
	public void withdrawNotice(String id, boolean success) {
		accountManager.withdrawNotice(id, success);
	}
}
