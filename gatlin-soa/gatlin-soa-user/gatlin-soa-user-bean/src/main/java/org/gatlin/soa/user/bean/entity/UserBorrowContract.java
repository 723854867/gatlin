package org.gatlin.soa.user.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class UserBorrowContract implements Identifiable<Long> {
	private static final long serialVersionUID = -2425163168014521597L;
	
	@Id
	private long id;
	private String investId;
	private long prodId;
	private BigDecimal amount;
	private String borrowName;
	private String borrowIdentity;
	private String borrowMobile;
	private String investName;
	private String investIdentity;
	private String investMobile;
	private String contract;
	private int created;
	private int updated;
	private String wqjContract;
	
	private int valueDate;
	private int expiryDate;
	private int  statues;

	public String getWqjContract() {
		return wqjContract;
	}
	public void setWqjContract(String wqjContract) {
		this.wqjContract = wqjContract;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public long getProdId() {
		return prodId;
	}
	public void setProdId(long prodId) {
		this.prodId = prodId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getBorrowIdentity() {
		return borrowIdentity;
	}
	public void setBorrowIdentity(String borrowIdentity) {
		this.borrowIdentity = borrowIdentity;
	}
	public String getBorrowMobile() {
		return borrowMobile;
	}
	public void setBorrowMobile(String borrowMobile) {
		this.borrowMobile = borrowMobile;
	}
	public String getInvestName() {
		return investName;
	}
	public void setInvestName(String investName) {
		this.investName = investName;
	}
	public String getInvestIdentity() {
		return investIdentity;
	}
	public void setInvestIdentity(String investIdentity) {
		this.investIdentity = investIdentity;
	}
	public String getInvestMobile() {
		return investMobile;
	}
	public void setInvestMobile(String investMobile) {
		this.investMobile = investMobile;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
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
	
	public int getValueDate() {
		return valueDate;
	}
	public void setValueDate(int valueDate) {
		this.valueDate = valueDate;
	}
	public int getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(int expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getStatues() {
		return statues;
	}
	public void setStatues(int statues) {
		this.statues = statues;
	}
	@Override
	public Long key() {
		return this.id;
	}

}
