package org.gatlin.soa.user.bean.param;

import java.math.BigDecimal;

import org.gatlin.soa.bean.param.SoaParam;

public class UserBorrowContractParam extends SoaParam  {
	private static final long serialVersionUID = -2425163168014521597L;
	
	private String investId;
	private long prodId;
	private BigDecimal amount;
	private String borrowName;
	private String borrowIdentity;
	private String borrowMobile;
	private String investName;
	private String investIdentity;
	private String investMobile;
	private int created;
	private int updated;
	
	
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

}
