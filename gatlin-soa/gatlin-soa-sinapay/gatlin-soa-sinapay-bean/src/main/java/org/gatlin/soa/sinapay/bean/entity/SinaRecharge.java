package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class SinaRecharge implements Identifiable<String> {

	private static final long serialVersionUID = 8366008745502022926L;
	
	@Id
	private String id;
	private String state;
	private String rechargee;
	private String recharger;
	private String accountType;
	private int created;
	private int updated;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
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
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
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
