package org.gatlin.soa.account.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.model.WithdrawContext;
import org.gatlin.soa.bean.param.WithdrawParam;

public interface AccountService {

	void userCreate(long uid, int mod);
	
	Account account(Query query);
	
	Account platAccount(AccountType type);
	
	Account userAccount(long uid, AccountType type);
	
	Account companyAccount(int companyId, AccountType type);
	
	Pager<Account> accounts(Query query);
	
	void process(AccountDetail detail);
	
	void recharge(Recharge recharge);
	
	void rechargeNotice(String id, RechargeState update);
	
	Pager<Recharge> recharges(Query query);
	
	Withdraw withdraw(WithdrawParam param, WithdrawContext context);
	
	void withdrawNotice(String id, boolean success);
	
	Pager<Withdraw> withdraws(Query query);
}
