package org.gatlin.soa.account.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.entity.LogAccount;
import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.GatlinBizType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.lang.StringUtil;

public class AccountDetail implements Serializable {

	private static final long serialVersionUID = -1674074777362407679L;

	private int bizType;
	private String bizId;
	private List<AccountTips> platformTips = new ArrayList<AccountTips>();
	private Map<Long, List<AccountTips>> userTips = new HashMap<Long, List<AccountTips>>();
	private Map<Long, List<AccountTips>> companyTips = new HashMap<Long, List<AccountTips>>();
	
	public AccountDetail() {}
	
	public AccountDetail(int bizType) {
		this.bizType = bizType;
	}
	
	public AccountDetail(Object bizId, int bizType) {
		this.bizType = bizType;
		this.bizId = bizId.toString();
	}
	
	public AccountDetail(Object bizId, GatlinBizType bizType) {
		this.bizId = bizId.toString();
		this.bizType = bizType.mark();
	}
	
	public AccountTips usableIncr(TargetType targetType, long owner, AccountType type, BigDecimal amount) {
		switch (targetType) {
		case USER:
			return userUsableIncr(owner, type, amount);
		case COMPANY:
			return companyUsableIncr(owner, type, amount);
		case PLATFORM:
			return platformUsableIncr(type, amount);
		default:
			throw new CodeException();
		}
	}
	
	public AccountTips usableDecr(TargetType targetType, long owner, AccountType type, BigDecimal amount) {
		switch (targetType) {
		case USER:
			return userUsableDecr(owner, type, amount);
		case COMPANY:
			return companyUsableDecr(owner, type, amount);
		case PLATFORM:
			return platformUsableDecr(type, amount);
		default:
			throw new CodeException();
		}
	}
	
	public AccountTips frozenIncr(TargetType targetType, long owner, AccountType type, BigDecimal amount) {
		switch (targetType) {
		case USER:
			return userFrozenIncr(owner, type, amount);
		case COMPANY:
			return companyFrozenIncr(owner, type, amount);
		case PLATFORM:
			return platformFrozenIncr(type, amount);
		default:
			throw new CodeException();
		}
	}
	
	public AccountTips frozenDecr(TargetType targetType, long owner, AccountType type, BigDecimal amount) {
		switch (targetType) {
		case USER:
			return userFrozenDecr(owner, type, amount);
		case COMPANY:
			return companyFrozenDecr(owner, type, amount);
		case PLATFORM:
			return platformFrozenDecr(type, amount);
		default:
			throw new CodeException();
		}
	}
	
	public AccountTips userUsableIncr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.USABLE).incr(amount);
		_wrap(uid, TargetType.USER, tips);
		return tips;
	}
	
	public AccountTips userFrozenIncr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.FROZEN).incr(amount);
		_wrap(uid, TargetType.USER, tips);
		return tips;
	}

	public AccountTips userUsableDecr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.USABLE).decr(amount);
		_wrap(uid, TargetType.USER, tips);
		return tips;
	}
	
	public AccountTips userFrozenDecr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.FROZEN).decr(amount);
		_wrap(uid, TargetType.USER, tips);
		return tips;
	}
	
	public AccountTips companyUsableIncr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.USABLE).incr(amount);
		_wrap(uid, TargetType.COMPANY, tips);
		return tips;
	}
	
	public AccountTips companyFrozenIncr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.FROZEN).incr(amount);
		_wrap(uid, TargetType.COMPANY, tips);
		return tips;
	}

	public AccountTips companyUsableDecr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.USABLE).decr(amount);
		_wrap(uid, TargetType.COMPANY, tips);
		return tips;
	}
	
	public AccountTips companyFrozenDecr(long uid, AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.FROZEN).decr(amount);
		_wrap(uid, TargetType.COMPANY, tips);
		return tips;
	}
	
	public AccountTips platformUsableIncr(AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.USABLE).incr(amount);
		platformTips.add(tips);
		return tips;
	}
	
	public AccountTips platformFrozenIncr(AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.FROZEN).incr(amount);
		platformTips.add(tips);
		return tips;
	}

	public AccountTips platformUsableDecr(AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.USABLE).decr(amount);
		platformTips.add(tips);
		return tips;
	}
	
	public AccountTips platformFrozenDecr(AccountType type, BigDecimal amount) {
		AccountTips tips = new AccountTips(type, AccountField.FROZEN).decr(amount);
		platformTips.add(tips);
		return tips;
	}
	
	private void _wrap(long owner, TargetType type, AccountTips tips) {
		Map<Long, List<AccountTips>> temp = type == TargetType.USER ? userTips : companyTips;
		List<AccountTips> list = type == TargetType.USER ? temp.get(owner) : temp.get(owner);
		if (null == list) {
			list = new ArrayList<AccountTips>();
			temp.put(owner, list);
		}
		list.add(tips);
	}
	
	public List<LogAccount> platformLogs() {
		List<LogAccount> logs = new ArrayList<LogAccount>();
		platformTips.forEach(tips -> {
			LogAccount log = new LogAccount();
			log.setOwner(0);
			log.setBizType(bizType);
			log.setAmount(tips.amount);
			log.setCreated(DateUtil.current());
			log.setFieldType(tips.field.mark());
			log.setAccountType(tips.type.mark());
			log.setId(IDWorker.INSTANCE.nextSid());
			log.setOwnerType(TargetType.PLATFORM.mark());
			log.setBizId(StringUtil.hasText(tips.bizId) ? tips.bizId : bizId);
			logs.add(log);
		});
		return logs;
	}
	
	public List<LogAccount> userLogs() {
		List<LogAccount> logs = new ArrayList<LogAccount>();
		userTips.entrySet().forEach(entry -> {
			entry.getValue().forEach(tips -> {
				LogAccount log = new LogAccount();
				log.setBizType(bizType);
				log.setAmount(tips.amount);
				log.setOwner(entry.getKey());
				log.setCreated(DateUtil.current());
				log.setFieldType(tips.field.mark());
				log.setAccountType(tips.type.mark());
				log.setId(IDWorker.INSTANCE.nextSid());
				log.setOwnerType(TargetType.USER.mark());
				log.setBizId(StringUtil.hasText(tips.bizId) ? tips.bizId : bizId);
				logs.add(log);
			});
		});
		return logs;
	}
	
	public List<LogAccount> companyLogs() {
		List<LogAccount> logs = new ArrayList<LogAccount>();
		companyTips.entrySet().forEach(entry -> {
			entry.getValue().forEach(tips -> {
				LogAccount log = new LogAccount();
				log.setBizType(bizType);
				log.setAmount(tips.amount);
				log.setOwner(entry.getKey());
				log.setCreated(DateUtil.current());
				log.setFieldType(tips.field.mark());
				log.setAccountType(tips.type.mark());
				log.setId(IDWorker.INSTANCE.nextSid());
				log.setOwnerType(TargetType.COMPANY.mark());
				log.setBizId(StringUtil.hasText(tips.bizId) ? tips.bizId : bizId);
				logs.add(log);
			});
		});
		return logs;
	}
	
	public static final class AccountTips implements Serializable {

		private static final long serialVersionUID = -1552564343449028081L;
		
		private String bizId;
		private AccountType type;
		private BigDecimal amount;
		private AccountField field;
		
		public AccountTips() {}
		
		public AccountTips(AccountType type, AccountField field) {
			this.type = type;
			this.field = field;
		}
		
		public AccountTips bizId(String bizId) {
			this.bizId = bizId;
			return this;
		}
		
		public AccountTips incr(BigDecimal amount) {
			this.amount = amount.abs();
			return this;
		}

		public AccountTips decr(BigDecimal amount) {
			this.amount = amount.abs().negate();
			return this;
		}
	}
}
