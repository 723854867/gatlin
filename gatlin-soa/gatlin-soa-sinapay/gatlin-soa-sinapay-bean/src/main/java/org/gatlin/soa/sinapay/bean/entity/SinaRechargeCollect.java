package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class SinaRechargeCollect implements Identifiable<String> {

	private static final long serialVersionUID = 3072145233275531189L;
	
	@Id
	private String id;
	private String rechargeId;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRechargeId() {
		return rechargeId;
	}
	
	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}
	
	@Override
	public String key() {
		return id;
	}
}
