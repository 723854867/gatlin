package org.gatlin.soa.sinapay.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.RepayType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;

public class BidInfo implements Serializable {

	private static final long serialVersionUID = 7828627102281570649L;

	private int duration;
	private String bizId;
	private long borrower;
	private String mobile;
	private BigDecimal rate;
	private TargetType btype;
	private String beginDate;
	private String expiryDate;
	private BigDecimal amount;
	private BidPurpose purpose;
	private RepayType repayType;

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getBizId() {
		return bizId;
	}
	
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	public long getBorrower() {
		return borrower;
	}
	
	public void setBorrower(long borrower) {
		this.borrower = borrower;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public TargetType getBtype() {
		return btype;
	}
	
	public void setBtype(TargetType btype) {
		this.btype = btype;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BidPurpose getPurpose() {
		return purpose;
	}
	
	public void setPurpose(BidPurpose purpose) {
		this.purpose = purpose;
	}

	public RepayType getRepayType() {
		return repayType;
	}

	public void setRepayType(RepayType repayType) {
		this.repayType = repayType;
	}
}
