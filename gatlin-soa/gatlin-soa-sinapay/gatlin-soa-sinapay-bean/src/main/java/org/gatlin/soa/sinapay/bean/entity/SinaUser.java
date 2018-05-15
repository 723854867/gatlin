package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class SinaUser implements Entity<String> {

	private static final long serialVersionUID = -3267822852357079661L;

	@Id
	private String sinaId;
	private String tid;
	private int type;
	private boolean realname;
	private int created;
	private int updated;

	public String getSinaId() {
		return sinaId;
	}

	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isRealname() {
		return realname;
	}
	
	public void setRealname(boolean realname) {
		this.realname = realname;
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
		return this.sinaId;
	}
}
