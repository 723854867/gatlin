package org.gatlin.soa.account.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.EntityGenerator;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.PlatType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.account.mybatis.dao.UserAccountDao;
import org.gatlin.soa.account.mybatis.dao.UserRechargeDao;
import org.gatlin.soa.account.plat.Alipay;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public void rechargeNotice(String id, boolean success) {
		Query query = new Query().eq("id", id).forUpdate();
		UserRecharge recharge = userRechargeDao.queryUnique(query);
		Assert.notNull(AccountCode.RECHARGE_NOT_EXIST, recharge);
		RechargeState state = RechargeState.match(recharge.getState());
		Assert.isTrue(state == RechargeState.INIT);
		RechargeState cstate = success ? RechargeState.SUCCESS : RechargeState.FAILURE;
		recharge.setState(cstate.mark());
		recharge.setUpdated(DateUtil.current());
		userRechargeDao.update(recharge);
	}
	
	public List<UserAccount> accounts(Query query) {
		return userAccountDao.queryList(query);
	}
}
