package org.gatlin.soa.account.bean.param;

import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.bean.enums.OS;
import org.gatlin.util.lang.StringUtil;

public class RechargesParam extends SoaParam {

	private static final long serialVersionUID = -2284951160155633004L;

	private OS os;
	private String id;
	private PlatType plat;
	private Long rechargee;
	private Long recharger;
	private String goodsId;
	private Integer goodsType;
	private Integer timeStop;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	
	public Integer getTimeStop() {
		return timeStop;
	}

	public void setTimeStop(Integer timeStop) {
		this.timeStop = timeStop;
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

	@Override
	public void verify() {
		super.verify();
		if (StringUtil.hasText(id))
			this.query.like("id", id);
		if (null != os)
			this.query.eq("os", os);
		if (null != plat)
			this.query.eq("plat", plat);
		if (null != state)
			this.query.eq("state", state);
		if (StringUtil.hasText(goodsId))
			this.query.eq("goods_id", goodsId);
		if (null != goodsType)
			this.query.eq("goods_type", goodsType);
		if (null != rechargee)
			this.query.eq("rechargee", rechargee);
		if (null != recharger)
			this.query.eq("recharger", recharger);
		if (null != timeStop)
			this.query.lte("created", timeStop);
		if (null != timeStart)
			this.query.gte("created", timeStart);
		if (null != rechargeeType)
			this.query.eq("rechargee_type", rechargeeType);
		if (null != rechargerType)
			this.query.eq("recharger_type", rechargerType);
		this.query.orderByDesc("created");
	}
}
