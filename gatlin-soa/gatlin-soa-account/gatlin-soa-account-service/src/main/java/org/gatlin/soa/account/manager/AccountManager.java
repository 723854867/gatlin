package org.gatlin.soa.account.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.bean.EntityGenerator;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.PlatType;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.account.mybatis.dao.UserAccountDao;
import org.gatlin.soa.account.mybatis.dao.UserRechargeDao;
import org.gatlin.soa.account.plat.Alipay;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Component;

@Component
public class AccountManager {

	@Resource
	private Alipay alipay;
	@Resource
	private UserAccountDao userAccountDao;
	@Resource
	private UserRechargeDao userRechargeDao;
	
	public void init(Long uid, int mod) {
		List<UserAccount> accounts = new ArrayList<UserAccount>();
		for (UserAccountType type : UserAccountType.values()) {
			if ((type.mark() & mod) == type.mark()) 
				accounts.add(EntityGenerator.newUserAccount(uid, type.mark()));
		}
		if (!CollectionUtil.isEmpty(accounts))
			userAccountDao.batchInsert(accounts);
	}
	
	public Object recharge(UserRecharge recharge) {
		userRechargeDao.insert(recharge);
		PlatType plat = PlatType.match(recharge.getPlat());
		switch (plat) {
		case ALIPAY:
			return alipay.appPay(recharge);
		default:
			break;
		}
		return recharge.getId();
	}
	
	public List<UserAccount> accounts(Query query) {
		return userAccountDao.queryList(query);
	}
}
