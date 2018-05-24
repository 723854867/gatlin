package org.gatlin.soa.sinapay.bean.param;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -2284951160155633004L;

	private Long rechargee;
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	private Integer companyId;
	private AccountType accountType;
	private TargetType rechargeeType;

	public Long getRechargee() {
		return rechargee;
	}
	
	public void setRechargee(Long rechargee) {
		this.rechargee = rechargee;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public TargetType getRechargeeType() {
		return rechargeeType;
	}
	
	public void setRechargeeType(TargetType rechargeeType) {
		this.rechargeeType = rechargeeType;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	@Override
	public void verify() {
		super.verify();
		// 对公账户充值只能充值到自己的对公账户
		if (null != companyId) {
			this.rechargee = Long.valueOf(companyId);
			this.rechargeeType = TargetType.COMPANY;
		}
		if (null == rechargeeType || null == rechargee) {
			this.rechargee = getUser().getId();
			this.rechargeeType = TargetType.USER;
		}
		if (null == accountType)
			this.accountType = AccountType.BASIC;
		this.amount.setScale(2, RoundingMode.UP);
	}
}
