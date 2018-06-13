package org.gatlin.soa.account.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.bean.enums.TargetType;

public interface AccountService {

	void init(TargetType ownerType, long owner, int mod);
	
	Account account(Query query);
	
	Account platAccount(AccountType type);
	
	Account userAccount(long uid, AccountType type);
	
	Account companyAccount(int companyId, AccountType type);
	
	Pager<Account> accounts(Query query);
	
	void process(AccountDetail detail);
	
	void recharge(Recharge recharge);
	
	void rechargeNotice(String id, RechargeState update);
	
	Recharge recharge(Query query);
	
	Pager<Recharge> recharges(Query query);
	
	void withdraw(Withdraw withdraw);
	
	void withdrawNotice(String id, boolean success);
	
	Pager<Withdraw> withdraws(Query query);
}
