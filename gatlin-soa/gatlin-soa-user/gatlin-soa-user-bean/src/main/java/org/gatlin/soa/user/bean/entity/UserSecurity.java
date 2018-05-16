package org.gatlin.soa.user.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class UserSecurity implements Identifiable<Long> {

	private static final long serialVersionUID = 8354553364333737685L;

	@Id
	private long uid;
	private String realname;
	private String identity;
	private String mobile;
	private int created;
	private int updated;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public Long key() {
		return this.uid;
	}
}
