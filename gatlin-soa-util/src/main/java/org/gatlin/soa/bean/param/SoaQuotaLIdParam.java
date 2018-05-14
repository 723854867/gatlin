package org.gatlin.soa.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class SoaQuotaLIdParam extends SoaIdParam {

	private static final long serialVersionUID = 1848130883002595944L;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal quota;
	
	public BigDecimal getQuota() {
		return quota;
	}
	
	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}
}
