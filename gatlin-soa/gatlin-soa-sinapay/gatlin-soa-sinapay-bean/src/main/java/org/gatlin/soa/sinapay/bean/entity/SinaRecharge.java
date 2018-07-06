package org.gatlin.soa.sinapay.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.util.bean.Identifiable;

public class SinaRecharge implements Identifiable<String> {

	private static final long serialVersionUID = 8366008745502022926L;
	
	@Id
	private String id;
	private String summary;
	private String rechargee;
	private String recharger;
	// 已经去掉了 user_fee 了，即这里的金额就是实际到账金额
	private BigDecimal amount;
	private RechargeState state;
	private TargetType rechargeeType;
	private TargetType rechargerType;
	private int created;
	private int updated;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public RechargeState getState() {
		return state;
	}
	
	public void setState(RechargeState state) {
		this.state = state;
	}
	
	public String getRechargee() {
		return rechargee;
	}
	
	public void setRechargee(String rechargee) {
		this.rechargee = rechargee;
	}
	
	public String getRecharger() {
		return recharger;
	}
	
	public void setRecharger(String recharger) {
		this.recharger = recharger;
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
	
	public TargetType getRechargerType() {
		return rechargerType;
	}
	
	public void setRechargerType(TargetType rechargerType) {
		this.rechargerType = rechargerType;
	}
	
	public int getCreated() {
		return created;
	}
	
	public void setCreated(int created) {
		this.created = created;
	}
	
	public int getUpdated() {
		return updated;
	}
	
	public void setUpdated(int updated) {
		this.updated = updated;
	}
	
	@Override
	public String key() {
		return id;
	}
}
