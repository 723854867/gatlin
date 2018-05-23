package org.gatlin.soa.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.enums.TargetType;

public class RechargeParam extends SoaParam {

	private static final long serialVersionUID = -2284951160155633004L;

	@Min(1)
	private int goodsType;
	private Long rechargee;
	@NotEmpty
	private String goodsId;
	private BigDecimal fee;
	private Integer companyId;
	private BigDecimal amount;
	private TargetType rechargeeType;

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
	
	public BigDecimal getFee() {
		return fee;
	}
	
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
		if (null == rechargeeType || null == rechargee) {
			this.rechargee = getUser().getId();
			this.rechargeeType = TargetType.USER;
		}
		if (rechargeeType == TargetType.PLATFORM)
			this.rechargee = 0l;
		if (1 == goodsType)
			Assert.notNull(CoreCode.PARAM_ERR, amount);
		if (null == fee)
			this.fee = BigDecimal.ZERO;
	}
}
