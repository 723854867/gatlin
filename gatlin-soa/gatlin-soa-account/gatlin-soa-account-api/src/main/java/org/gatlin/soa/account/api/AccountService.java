package org.gatlin.soa.account.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;

public interface AccountService {

	void init(long uid, int mod);
	
	Pager<UserAccount> accounts(Query query);
	
	Object recharge(UserRecharge recharge);
	
	void rechargeNotice(String id, RechargeState update);
}
