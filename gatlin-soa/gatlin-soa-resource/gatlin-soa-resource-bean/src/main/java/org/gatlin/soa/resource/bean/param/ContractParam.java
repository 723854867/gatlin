package org.gatlin.soa.resource.bean.param;

import java.math.BigDecimal;

import org.gatlin.soa.bean.param.SoaParam;

public class ContractParam extends SoaParam {

	private static final long serialVersionUID = 4684184585876486964L;

	private String orderNo;
	
	private String amountStr;
	
	private String investRealName;

	private String investCard;

	private String investMobile;

	private String borrowMobile;
	
	private String borrowRealName;

	private String borrowCard;

	private BigDecimal rate;

	private BigDecimal amount;

	private String interestType;

	private String contractDate;

	private String valueDate;

	private String expiryDate;

	private String resourceUrl;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getInvestRealName() {
		return investRealName;
	}

	public void setInvestRealName(String investRealName) {
		this.investRealName = investRealName;
	}

	public String getInvestCard() {
		return investCard;
	}

	public void setInvestCard(String investCard) {
		this.investCard = investCard;
	}

	public String getBorrowRealName() {
		return borrowRealName;
	}

	public void setBorrowRealName(String borrowRealName) {
		this.borrowRealName = borrowRealName;
	}

	public String getBorrowCard() {
		return borrowCard;
	}

	public void setBorrowCard(String borrowCard) {
		this.borrowCard = borrowCard;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getInterestType() {
		return interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getInvestMobile() {
		return investMobile;
	}

	public void setInvestMobile(String investMobile) {
		this.investMobile = investMobile;
	}

	public String getBorrowMobile() {
		return borrowMobile;
	}

	public void setBorrowMobile(String borrowMobile) {
		this.borrowMobile = borrowMobile;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

}
