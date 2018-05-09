package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class UserFrozen implements Entity<String> {

	private static final long serialVersionUID = 7088830119964678890L;

	@Id
	private String id;
	private int state;
	private int bizType;
	private String bizId;
	private BigDecimal amount;
	private int created;
	private int updated;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public String key() {
		return this.id;
	}
}
