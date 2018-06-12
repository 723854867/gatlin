package org.gatlin.soa.sinapay.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaLidParam;

public class RechargeCompanyParam extends SoaLidParam {

	private static final long serialVersionUID = -4153003202832703710L;

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
