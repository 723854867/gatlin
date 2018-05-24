package org.gatlin.soa.user.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class UserBind implements Identifiable<Long> {

	private static final long serialVersionUID = -7494183646632938979L;

	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private int plat;
	private String identity;
	private int created;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getPlat() {
		return plat;
	}
	
	public void setPlat(int plat) {
		this.plat = plat;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public Long key() {
		return this.id;
	}
}
