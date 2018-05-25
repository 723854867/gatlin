package org.gatlin.soa.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.enums.AccountType;

public class WithdrawParam extends SoaParam {

	private static final long serialVersionUID = 4460675374479082610L;

	// 提现金额
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	// 提现账户
	@NotNull
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
}
