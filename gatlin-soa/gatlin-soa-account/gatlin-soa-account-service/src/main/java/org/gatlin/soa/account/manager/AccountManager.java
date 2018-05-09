package org.gatlin.soa.account.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.EntityGenerator;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.LogUserAccount;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.entity.UserFrozen;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.account.bean.enums.FrozenState;
import org.gatlin.soa.account.bean.enums.PlatType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.account.istate.RechargeStateMachine;
import org.gatlin.soa.account.mybatis.dao.LogUserAccountDao;
import org.gatlin.soa.account.mybatis.dao.UserAccountDao;
import org.gatlin.soa.account.mybatis.dao.UserFrozenDao;
import org.gatlin.soa.account.mybatis.dao.UserRechargeDao;
import org.gatlin.soa.account.plat.Alipay;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountManager {

	@Resource
	private Alipay alipay;
	@Resource
	private UserFrozenDao userFrozenDao;
	@Resource
	private UserAccountDao userAccountDao;
	@Resource
	private UserRechargeDao userRechargeDao;
	@Resource
	private LogUserAccountDao logUserAccountDao; 
	@Autowired
	private Map<String, RechargeStateMachine> rechargeStateMachines;
	
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
	public void rechargeNotice(String id, RechargeState update) {
		Query query = new Query().eq("id", id).forUpdate();
		UserRecharge recharge = userRechargeDao.queryUnique(query);
		Assert.notNull(AccountCode.RECHARGE_NOT_EXIST, recharge);
		RechargeState state = RechargeState.match(recharge.getState());
		RechargeStateMachine stateMachine = rechargeStateMachines.get(state.machineName());
		stateMachine.update(recharge, update);
		userRechargeDao.update(recharge);
	}
	
	@Transactional
	public void process(LogUserAccount log) {
		if (log.getAmount().compareTo(BigDecimal.ZERO) == 0)
			return;
		Query query = new Query().eq("type", log.getAccountType()).eq("uid", log.getUid()).forUpdate();
		UserAccount account = account(query);
		AccountField field = AccountField.match(log.getFieldType());
		switch (field) {
		case USABLE:
			log.setPreAmount(account.getUsable());
			account.setUsable(account.getUsable().add(log.getAmount()));
			log.setPostAmount(account.getUsable());
			break;
		case FROZEN:
			log.setPreAmount(account.getFrozen());
			account.setFrozen(account.getFrozen().add(log.getAmount()));
			log.setPostAmount(account.getFrozen());
			if (log.getAmount().compareTo(BigDecimal.ZERO) > 0) {			// 冻结
				UserFrozen frozen = EntityGenerator.newUserFrozen(log);
				userFrozenDao.insert(frozen);
			} else {														// 解冻
				query = new Query().eq("biz_type", log.getBizType()).eq("biz_id", log.getBizId());
				UserFrozen frozen = userFrozenDao.queryUnique(query);
				Assert.isTrue(AccountCode.FROZEN_AMOUNT_ERR, frozen.getAmount().compareTo(log.getAmount().negate()) == 0);
				frozen.setState(FrozenState.THAW.mark());
				frozen.setUpdated(DateUtil.current());
				userFrozenDao.update(frozen);
			}
			break;
		default:
			throw new CodeException();
		}
		account.setUpdated(DateUtil.current());
		Assert.isTrue(AccountCode.USABLE_LACK, account.getUsable().compareTo(BigDecimal.ZERO) >= 0);
		Assert.isTrue(AccountCode.FROZEN_LACK, account.getFrozen().compareTo(BigDecimal.ZERO) >= 0);
		userAccountDao.update(account);
		logUserAccountDao.insert(log);
	}
	
	public UserAccount account(Query query) {
		return userAccountDao.queryUnique(query);
	}
	
	public List<UserAccount> accounts(Query query) {
		return userAccountDao.queryList(query);
	}
}
