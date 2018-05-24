package org.gatlin.soa.account.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.account.istate.RechargeStateMachine;
import org.gatlin.soa.account.mybatis.dao.AccountDao;
import org.gatlin.soa.account.mybatis.dao.LogAccountDao;
import org.gatlin.soa.account.mybatis.dao.RechargeDao;
import org.gatlin.soa.account.mybatis.dao.WithdrawDao;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.BizType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.enums.WithdrawState;
import org.gatlin.soa.bean.param.WithdrawParam;
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
	private WithdrawDao withdrawDao;
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
	public Withdraw withdraw(WithdrawParam param) {
		Withdraw withdraw = EntityGenerator.newWithdraw(param);
		withdrawDao.insert(withdraw);
		AccountDetail detail = new AccountDetail(withdraw.getId(), BizType.WITHDRAW);
		BigDecimal amount = param.getAmount().add(param.getFee());
		detail.usableDecr(param.getWithdraweeType(), param.getWithdrawee(), param.getAccountType(), amount);
		detail.frozenIncr(param.getWithdraweeType(), param.getWithdrawee(), param.getAccountType(), amount);
		process(detail);
		return withdraw;
	}
	
	@Transactional
	public void withdrawNotice(String id, boolean success) {
		Query query = new Query().eq("id", id).forUpdate();
		Withdraw withdraw = withdrawDao.queryUnique(query);
		Assert.notNull(CoreCode.WITHDRAW_NOT_EXIST, withdraw);
		WithdrawState state = WithdrawState.match(withdraw.getState());
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, state == WithdrawState.INIT);
		withdraw.setState(success ? WithdrawState.SUCCESS.mark() : WithdrawState.FAILURE.mark());
		withdraw.setUpdated(DateUtil.current());
		withdrawDao.update(withdraw);
		AccountDetail detail = new AccountDetail(withdraw.getId(), success ? BizType.WITHDRAW_SUCCESS : BizType.WITHDRAW_FAILURE);
		BigDecimal amount = withdraw.getAmount().add(withdraw.getFee());
		TargetType withdrawerType = TargetType.match(withdraw.getWithdrawerType());
		AccountType withdrawerAccountType = AccountType.match(withdraw.getWithdrawerAccountType());
		detail.frozenDecr(withdrawerType, withdraw.getWithdrawer(), withdrawerAccountType, amount);
		if (!success) 
			detail.usableIncr(withdrawerType, withdraw.getWithdrawer(), withdrawerAccountType, amount);
		process(detail);
	}
	
	@Transactional
	public void process(AccountDetail detail) {
		List<LogAccount> logs = detail.platformLogs();
		if (!CollectionUtil.isEmpty(logs)) {
			Set<Integer> set = new HashSet<Integer>();
			logs.forEach(log -> set.add(log.getAccountType()));
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
		_process(accounts, logs);
	}
	
	private void _process(List<Account> accounts, List<LogAccount> logs)  {
		logs.forEach(log -> {
			Iterator<Account> itr = accounts.iterator();
			while (itr.hasNext()) {
				Account account = itr.next();
				if (account.getOwner() == log.getOwner() && account.getType() == log.getAccountType()) {
					AccountField field = AccountField.match(log.getFieldType());
					switch (field) {
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
					break;
				}
				Assert.isTrue(CoreCode.USABLE_LACK, account.getUsable().compareTo(BigDecimal.ZERO) >= 0);
				Assert.isTrue(CoreCode.FROZEN_LACK, account.getFrozen().compareTo(BigDecimal.ZERO) >= 0);
			}
		});
		logAccountDao.batchInsert(logs);
		accountDao.updateCollection(accounts);
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
