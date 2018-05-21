package org.gatlin.soa.account.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.EntityGenerator;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.LogAccount;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.account.istate.RechargeStateMachine;
import org.gatlin.soa.account.mybatis.dao.AccountDao;
import org.gatlin.soa.account.mybatis.dao.LogAccountDao;
import org.gatlin.soa.account.mybatis.dao.RechargeDao;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountManager {

	@Resource
	private AccountDao accountDao;
	@Resource
	private RechargeDao rechargeDao;
	@Resource
	private LogAccountDao logAccountDao; 
	@Autowired
	private Map<String, RechargeStateMachine> rechargeStateMachines;
	
	public void userCreate(Long uid, int mod) {
		List<Account> accounts = new ArrayList<Account>();
		for (AccountType type : AccountType.values()) {
			if ((type.mark() & mod) == type.mark()) 
				accounts.add(EntityGenerator.newUserAccount(uid, type.mark()));
		}
		if (!CollectionUtil.isEmpty(accounts))
			accountDao.batchInsert(accounts);
	}
	
	public void recharge(Recharge recharge) {
		rechargeDao.insert(recharge);
	}
	
	@Transactional
	public void rechargeNotice(String id, RechargeState update) {
		Query query = new Query().eq("id", id).forUpdate();
		Recharge recharge = rechargeDao.queryUnique(query);
		Assert.notNull(AccountCode.RECHARGE_NOT_EXIST, recharge);
		RechargeState state = RechargeState.match(recharge.getState());
		RechargeStateMachine stateMachine = rechargeStateMachines.get(state.machineName());
		stateMachine.update(recharge, update);
		rechargeDao.update(recharge);
	}
	
	@Transactional
	public void process(AccountDetail detail) {
		if (detail.amount().compareTo(BigDecimal.ZERO) == 0)
			return;
		Query query = new Query().eq("type", detail.accountType().mark()).eq("owner_type", detail.ownerType().mark()).eq("owner", detail.owner()).forUpdate();
		Account account = account(query);
		LogAccount log = _process(account, detail);
		logAccountDao.insert(log);
		accountDao.update(account);
	}
	
	@Transactional
	public void process(List<AccountDetail> details) { 
		List<LogAccount> logs = new ArrayList<LogAccount>();
		Map<String, Account> accounts = new HashMap<String, Account>();
		for (AccountDetail detail : details) {
			if (detail.amount().compareTo(BigDecimal.ZERO) == 0)
				continue;
			String key = detail.ownerType().mark() + "_" + detail.owner() + "_" + detail.accountType().mark();
			Account account = accounts.get(key);
			if (null == account) {
				Query query = new Query().eq("type", detail.accountType().mark()).eq("owner_type", detail.ownerType().mark()).eq("owner", detail.owner()).forUpdate();
				account = account(query);
				accounts.put(key, account);
			}
			LogAccount log = _process(account, detail);
			logs.add(log);
		}
		if (!CollectionUtil.isEmpty(logs))
			logAccountDao.batchInsert(logs);
		if (CollectionUtil.isEmpty(accounts))
			accountDao.updateCollection(accounts.values());
	}
	
	private LogAccount _process(Account account, AccountDetail detail) { 
		LogAccount log = detail.log();
		switch (detail.field()) {
		case USABLE:
			log.setPreAmount(account.getUsable());
			account.setUsable(account.getUsable().add(log.getAmount()));
			log.setPostAmount(account.getUsable());
			break;
		case FROZEN:
			log.setPreAmount(account.getFrozen());
			account.setFrozen(account.getFrozen().add(log.getAmount()));
			log.setPostAmount(account.getFrozen());
			break;
		default:
			throw new CodeException();
		}
		account.setUpdated(DateUtil.current());
		Assert.isTrue(AccountCode.USABLE_LACK, account.getUsable().compareTo(BigDecimal.ZERO) >= 0);
		Assert.isTrue(AccountCode.FROZEN_LACK, account.getFrozen().compareTo(BigDecimal.ZERO) >= 0);
		return log;
	}
	
	public Account platAccount(AccountType type) {
		Query query = new Query().eq("type", type.mark()).eq("owner_type", TargetType.PLATFORM.mark());
		return account(query);
	}
	
	public Account userAccount(long uid, AccountType type) {
		Query query = new Query().eq("type", type.mark()).eq("owner_type", TargetType.USER.mark()).eq("owner", uid);
		return account(query);
	}
	
	public Account companyAccount(int companyId, AccountType type) {
		Query query = new Query().eq("type", type.mark()).eq("owner_type", TargetType.COMPANY.mark()).eq("owner", companyId);
		return account(query);
	}
	
	public Account account(Query query) {
		return accountDao.queryUnique(query);
	}
	
	public List<Account> accounts(Query query) {
		return accountDao.queryList(query);
	}
	
	public List<Recharge> recharges(Query query) {
		return rechargeDao.queryList(query);
	}
}
