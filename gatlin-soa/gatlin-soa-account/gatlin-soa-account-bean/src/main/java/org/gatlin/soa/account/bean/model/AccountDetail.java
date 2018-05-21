package org.gatlin.soa.account.bean.model;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.LogAccount;
import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.lang.StringUtil;

public class AccountDetail {

	private long owner;
	private int bizType;
	private String bizId;
	private BigDecimal amount;
	private AccountField field;
	private TargetType ownerType;
	private AccountType accountType;

	private AccountDetail(TargetType ownerType, AccountField field, long owner) {
		this.field = field;
		this.owner = owner;
		this.ownerType = ownerType;
		bizId(StringUtil.EMPTY);
		accountType(AccountType.BASIC);
	}

	public AccountDetail bizId(Object bizId) {
		this.bizId = bizId.toString();
		return this;
	}

	public AccountDetail bizType(int bizType) {
		this.bizType = bizType;
		return this;
	}

	public AccountDetail incr(BigDecimal amount) {
		this.amount = amount.abs();
		return this;
	}

	public AccountDetail decr(BigDecimal amount) {
		this.amount = amount.abs().negate();
		return this;
	}

	// 默认为基本账户
	public AccountDetail accountType(AccountType accountType) {
		this.accountType = accountType;
		return this;
	}

	public long owner() {
		return owner;
	}

	public int bizType() {
		return bizType;
	}

	public String bizId() {
		return bizId;
	}

	public BigDecimal amount() {
		return amount;
	}

	public AccountField field() {
		return field;
	}

	public AccountType accountType() {
		return accountType;
	}

	public TargetType ownerType() {
		return ownerType;
	}
	
	public LogAccount log() {
		LogAccount log = new LogAccount();
		log.setId(IDWorker.INSTANCE.nextSid());
		log.setOwner(owner);
		log.setBizId(bizId);
		log.setAmount(amount);
		log.setBizType(bizType);
		log.setFieldType(field.mark());
		log.setOwnerType(ownerType.mark());
		log.setAccountType(accountType.mark());
		log.setCreated(DateUtil.current());
		return log;
	}

	public static final AccountDetail platUsable() {
		return new AccountDetail(TargetType.PLATFORM, AccountField.USABLE, 0);
	}

	public static final AccountDetail platFrozen() {
		return new AccountDetail(TargetType.PLATFORM, AccountField.FROZEN, 0);
	}

	public static final AccountDetail userUsable(long uid) {
		return new AccountDetail(TargetType.USER, AccountField.USABLE, uid);
	}

	public static final AccountDetail userFrozen(long uid) {
		return new AccountDetail(TargetType.USER, AccountField.FROZEN, uid);
	}
	
	public static final AccountDetail companyUsable(long companyId) {
		return new AccountDetail(TargetType.COMPANY, AccountField.USABLE, companyId);
	}

	public static final AccountDetail companyFrozen(long companyId) {
		return new AccountDetail(TargetType.COMPANY, AccountField.FROZEN, companyId);
	}
}
