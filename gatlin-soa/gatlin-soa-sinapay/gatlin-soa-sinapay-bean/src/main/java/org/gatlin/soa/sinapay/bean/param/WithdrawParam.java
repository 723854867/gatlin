package org.gatlin.soa.sinapay.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;

public class WithdrawParam extends SoaParam {

	private static final long serialVersionUID = 4460675374479082610L;

	// 提现金额
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
