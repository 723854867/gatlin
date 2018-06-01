package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.bean.Identifiable;

public class LogAccount implements Identifiable<String> {

	private static final long serialVersionUID = 8848537024255209715L;

	@Id
	private String id;
	private long owner;
	private int bizType;
	private String bizId;
	private BigDecimal amount;
	private TargetType ownerType;
	private BigDecimal preAmount;
	private BigDecimal postAmount;
	private AccountField fieldType;
	private AccountType accountType;
	private int created;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public TargetType getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(TargetType ownerType) {
		this.ownerType = ownerType;
	}

	public BigDecimal getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(BigDecimal preAmount) {
		this.preAmount = preAmount;
	}

	public BigDecimal getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(BigDecimal postAmount) {
		this.postAmount = postAmount;
	}
	
	public AccountField getFieldType() {
		return fieldType;
	}
	
	public void setFieldType(AccountField fieldType) {
		this.fieldType = fieldType;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String key() {
		return id;
	}
}
