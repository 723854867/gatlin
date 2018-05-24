package org.gatlin.soa.alipay.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.param.SoaParam;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -6068871705392042154L;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	private AccountType accountType;
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
		if (null == accountType)
			this.accountType = AccountType.BASIC;
	}
}
