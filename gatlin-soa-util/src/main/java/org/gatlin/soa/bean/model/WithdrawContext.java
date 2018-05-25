package org.gatlin.soa.bean.model;

import java.math.BigDecimal;

import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.param.WithdrawParam;

public class WithdrawContext extends ValidatorContext<WithdrawParam> {

	private static final long serialVersionUID = 6162887517459761906L;

	private PlatType plat;
	private BigDecimal fee;
	
	public WithdrawContext() {}
	
	public WithdrawContext(PlatType plat, WithdrawParam param) {
		super(param);
		this.plat = plat;
		this.fee = BigDecimal.ZERO;
	}
	
	public PlatType getPlat() {
		return plat;
	}
	
	public void setPlat(PlatType plat) {
		this.plat = plat;
	}
	
	public BigDecimal getFee() {
		return fee;
	}
	
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
