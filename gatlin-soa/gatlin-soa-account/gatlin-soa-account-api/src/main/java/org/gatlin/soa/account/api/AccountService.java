package org.gatlin.soa.account.api;

import java.util.List;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;

public interface AccountService {

	void userCreate(long uid, int mod);
	
	void process(AccountDetail detail);
	
	// 该方法谨慎使用，如果涉及到的账户少可以通过该方法直接操作，如果账户数量较大推荐使用队列的方式
	void process(List<AccountDetail> details);
	
	Account account(Query query);
	
	Account platAccount(AccountType type);
	
	Account userAccount(long uid, AccountType type);
	
	Account companyAccount(int companyId, AccountType type);
	
	Pager<Account> accounts(Query query);
	
	void recharge(Recharge recharge);
	
	void rechargeNotice(String id, RechargeState update);
}
