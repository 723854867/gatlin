package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class SinaPay implements Identifiable<String> {

	private static final long serialVersionUID = -9186319834645418494L;

	@Id
	private String id;
	private String state;
	private String withdrawId;
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
	
	public String getWithdrawId() {
		return withdrawId;
	}
	
	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
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
		return this.id;
	}
}