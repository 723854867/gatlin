package org.gatlin.soa.account.bean.param;

import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.util.bean.enums.OS;

public class CompanyRechargesParam extends SoaLidParam {

	private static final long serialVersionUID = -5292099230901441072L;

	private OS os;
	private PlatType plat;
	private Long rechargee;
	private Long recharger;
	private String goodsId;
	private Integer timeStop;
	private String rechargeId;
	private Integer goodsType;
	private Integer timeStart;
	private RechargeState state;
	private TargetType rechargeeType;
	private TargetType rechargerType;

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public PlatType getPlat() {
		return plat;
	}

	public void setPlat(PlatType plat) {
		this.plat = plat;
	}

	public Long getRechargee() {
		return rechargee;
	}

	public void setRechargee(Long rechargee) {
		this.rechargee = rechargee;
	}

	public Long getRecharger() {
		return recharger;
	}

	public void setRecharger(Long recharger) {
		this.recharger = recharger;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getTimeStop() {
		return timeStop;
	}

	public void setTimeStop(Integer timeStop) {
		this.timeStop = timeStop;
	}

	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Integer timeStart) {
		this.timeStart = timeStart;
	}

	public RechargeState getState() {
		return state;
	}

	public void setState(RechargeState state) {
		this.state = state;
	}

	public TargetType getRechargeeType() {
		return rechargeeType;
	}

	public void setRechargeeType(TargetType rechargeeType) {
		this.rechargeeType = rechargeeType;
	}

	public TargetType getRechargerType() {
		return rechargerType;
	}

	public void setRechargerType(TargetType rechargerType) {
		this.rechargerType = rechargerType;
	}
}
