package org.gatlin.soa.sinapay.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -6068871705392042154L;

	private Long rechargee;
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	@NotNull
	private AccountType accountType;
	private TargetType rechargeeType;
	
	public Long getRechargee() {
		return rechargee;
	}
	
	public void setRechargee(Long rechargee) {
		this.rechargee = rechargee;
	}
	
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
	
	public TargetType getRechargeeType() {
		return rechargeeType;
	}
	
	public void setRechargeeType(TargetType rechargeeType) {
		this.rechargeeType = rechargeeType;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null == rechargee || null == rechargeeType) {
			this.rechargee = getUser().getId();
			this.rechargeeType = TargetType.USER;
		}
		Assert.isTrue(CoreCode.PARAM_ERR, rechargeeType != TargetType.PLATFORM);
	}
}
