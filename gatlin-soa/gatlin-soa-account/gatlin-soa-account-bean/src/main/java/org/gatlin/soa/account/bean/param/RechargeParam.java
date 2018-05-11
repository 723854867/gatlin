package org.gatlin.soa.account.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.account.bean.enums.PlatType;
import org.gatlin.soa.bean.param.SoaParam;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -2284951160155633004L;

	private PlatType plat;
	@Min(1)
	private int goodsType;
	private Long rechargee;
	@NotEmpty
	private String goodsId;
	private BigDecimal amount;

	public PlatType getPlat() {
		return plat;
	}

	public void setPlat(PlatType plat) {
		this.plat = plat;
	}
	
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
