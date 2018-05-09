package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class LogUserAccount implements Entity<String> {

	private static final long serialVersionUID = 8848537024255209715L;

	@Id
	private String id;
	private long uid;
	private int bizType;
	private String bizId;
	private int fieldType;
	private int accountType;
	private BigDecimal amount;
	private BigDecimal preAmount;
	private BigDecimal postAmount;
	private int created;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
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

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
