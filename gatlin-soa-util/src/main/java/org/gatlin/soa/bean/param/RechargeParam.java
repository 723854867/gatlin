package org.gatlin.soa.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -2284951160155633004L;

	@Min(1)
	private int goodsType;
	private Long rechargee;
	@NotEmpty
	private String goodsId;
	private BigDecimal amount;

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	
	public Long getRechargee() {
		return rechargee;
	}
	
	public void setRechargee(Long rechargee) {
		this.rechargee = rechargee;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Override
	public void verify() {
		super.verify();
	}
}
