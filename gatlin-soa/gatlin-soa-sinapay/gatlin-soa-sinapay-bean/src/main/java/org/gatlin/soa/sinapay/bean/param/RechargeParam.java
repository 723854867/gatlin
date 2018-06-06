package org.gatlin.soa.sinapay.bean.param;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -6068871705392042154L;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Override
	public void verify() {
		super.verify();
		this.amount = amount.setScale(2, RoundingMode.UP);
	}
}
