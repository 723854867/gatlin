package org.gatlin.soa.account.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.EntityGenerator;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.LogAccount;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.WithdrawState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.account.hook.HookContainer;
import org.gatlin.soa.account.mybatis.dao.AccountDao;
import org.gatlin.soa.account.mybatis.dao.LogAccountDao;
import org.gatlin.soa.account.mybatis.dao.RechargeDao;
import org.gatlin.soa.account.mybatis.dao.WithdrawDao;
import org.gatlin.soa.bean.enums.GatlinBizType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountManager {

	@Resource
	private AccountDao accountDao;
	@Resource
	private RechargeDao rechargeDao;
	@Resource
	private WithdrawDao withdrawDao;
	@Resource
	private LogAccountDao logAccountDao; 
	
	public void init(TargetType ownerType, long owner, int mod) {
		List<Account> accounts = new ArrayList<Account>();
		for (AccountType type : AccountType.values()) {
			if ((type.mark() & mod) == type.mark()) 
				accounts.add(EntityGenerator.newAccount(ownerType, owner, type));
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
		HookContainer.rechageNoticeHook().process(recharge, update);
		rechargeDao.update(recharge);
	}
	
	@Transactional
	public void withdraw(Withdraw withdraw) {
		withdrawDao.insert(withdraw);
		AccountDetail detail = new AccountDetail(withdraw.getId(), GatlinBizType.WITHDRAW);
		BigDecimal amount = withdraw.getAmount().add(withdraw.getFee());
		detail.usableDecr(withdraw.getWithdrawerType(), withdraw.getWithdrawer(), withdraw.getAccountType(), amount);
		detail.frozenIncr(withdraw.getWithdrawerType(), withdraw.getWithdrawer(), withdraw.getAccountType(), amount);
		process(detail);
	}
	
	@Transactional
	public void withdrawNotice(String id, boolean success) {
		Query query = new Query().eq("id", id).forUpdate();
		Withdraw withdraw = withdrawDao.queryUnique(query);
		Assert.notNull(CoreCode.WITHDRAW_NOT_EXIST, withdraw);
		WithdrawState state = withdraw.getState();
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, state == WithdrawState.INIT);
		withdraw.setState(success ? WithdrawState.SUCCESS : WithdrawState.FAILURE);
		withdraw.setUpdated(DateUtil.current());
		withdrawDao.update(withdraw);
		AccountDetail detail = new AccountDetail(withdraw.getId(), success ? GatlinBizType.WITHDRAW_SUCCESS : GatlinBizType.WITHDRAW_FAILURE);
		BigDecimal amount = withdraw.getAmount().add(withdraw.getFee());
		detail.frozenDecr(withdraw.getWithdrawerType(), withdraw.getWithdrawer(), withdraw.getAccountType(), amount);
		if (!success) 
			detail.usableIncr(withdraw.getWithdrawerType(), withdraw.getWithdrawer(), withdraw.getAccountType(), amount);
		process(detail);
	}
	
	@Transactional
	public void process(AccountDetail detail) {
		List<LogAccount> logs = detail.platformLogs();
		if (!CollectionUtil.isEmpty(logs)) {
			Set<Integer> set = new HashSet<Integer>();
			logs.forEach(log -> set.add(log.getAccountType().mark()));
			Query query = new Query().eq("owner_type", TargetType.PLATFORM.mark()).forUpdate();
			List<Account> accounts = accountDao.queryList(query);
			_process(accounts, logs);
		}
		_process(detail, TargetType.USER);
		_process(detail, TargetType.COMPANY);
	}
	
	private void _process(AccountDetail detail, TargetType type) {
		List<LogAccount> logs = type == TargetType.USER ? detail.userLogs() : detail.companyLogs();
		if (CollectionUtil.isEmpty(logs))
			return;
		Set<Long> set = new HashSet<Long>();
		logs.forEach(log -> set.add(log.getOwner()));
		Query query = new Query().eq("owner_type", type.mark()).in("owner", set).forUpdate();
		List<Account> accounts = accountDao.queryList(query);
		if (CollectionUtil.isEmpty(accounts))
			return;
		_process(accounts, logs);
	}
	
	private void _process(List<Account> accounts, List<LogAccount> logs)  {
		logs.forEach(log -> {
			Iterator<Account> itr = accounts.iterator();
			while (itr.hasNext()) {
				Account account = itr.next();
				if (account.getOwner() == log.getOwner() && account.getType() == log.getAccountType()) {
					switch (log.getFieldType()) {
					case FROZEN:
						log.setPreAmount(account.getFrozen());
						account.setUpdated(DateUtil.current());
						account.setFrozen(account.getFrozen().add(log.getAmount()));
						log.setPostAmount(account.getFrozen());
						break;
					case USABLE:
						log.setPreAmount(account.getUsable());
						account.setUpdated(DateUtil.current());
						account.setUsable(account.getUsable().add(log.getAmount()));
						log.setPostAmount(account.getUsable());
						break;
					default:
						throw new CodeException();
					}
					Assert.isTrue(CoreCode.USABLE_LACK, account.getUsable().compareTo(BigDecimal.ZERO) >= 0);
					Assert.isTrue(CoreCode.FROZEN_LACK, account.getFrozen().compareTo(BigDecimal.ZERO) >= 0);
					break;
				}
			}
		});
		if (!CollectionUtil.isEmpty(logs))
			logAccountDao.batchInsert(logs);
		accountDao.updateCollection(accounts);
	}
	
	public Account platAccount(AccountType type) {
		Query query = new Query().eq("type", type.mark()).eq("owner_type", TargetType.PLATFORM);
		return account(query);
	}
	
	public Account userAccount(long uid, AccountType type) {
		Query query = new Query().eq("type", type.mark()).eq("owner_type", TargetType.USER).eq("owner", uid);
		return account(query);
	}
	
	public Account companyAccount(int companyId, AccountType type) {
		Query query = new Query().eq("type", type.mark()).eq("owner_type", TargetType.COMPANY).eq("owner", companyId);
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
	
	public List<Withdraw> withdraws(Query query) {
		return withdrawDao.queryList(query);
	}
}
