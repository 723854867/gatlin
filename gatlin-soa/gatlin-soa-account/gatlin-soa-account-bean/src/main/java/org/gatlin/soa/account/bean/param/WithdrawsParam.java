package org.gatlin.soa.account.bean.param;

import org.gatlin.soa.account.bean.enums.WithdrawState;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.bean.enums.OS;

public class WithdrawsParam extends SoaParam {

	private static final long serialVersionUID = -2951701921579464535L;

	private OS os;
	private Long uid;
	private String id;
	private PlatType plat;
	private Integer timeStop;
	private Integer timeStart;
	private WithdrawState state;
	private TargetType withdrawerType;

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

	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
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

	public WithdrawState getState() {
		return state;
	}

	public void setState(WithdrawState state) {
		this.state = state;
	}

	public TargetType getWithdrawerType() {
		return withdrawerType;
	}

	public void setWithdrawerType(TargetType withdrawerType) {
		this.withdrawerType = withdrawerType;
	}

	@Override
	public void verify() {
		super.verify();
		if (null != os)
			this.query.eq("os", os);
		if (null != id)
			this.query.eq("id", id);
		if (null != plat)
			this.query.eq("plat", plat);
		if (null != uid)
			this.query.eq("uid", uid);
		if (null != state)
			this.query.eq("state", state);
		if (null != withdrawerType)
			this.query.eq("withdrawer_type", withdrawerType);
		this.query.orderByDesc("created");
	}
}
