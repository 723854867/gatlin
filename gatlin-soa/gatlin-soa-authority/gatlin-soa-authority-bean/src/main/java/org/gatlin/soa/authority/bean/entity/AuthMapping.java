package org.gatlin.soa.authority.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.soa.authority.bean.enums.AuthMappingType;
import org.gatlin.util.bean.Identifiable;

public class AuthMapping implements Identifiable<Long> {

	private static final long serialVersionUID = -517918930137921374L;

	@Id
	@GeneratedValue
	private long id;
	private long sid;
	private long tid;
	private AuthMappingType type;
	private int created;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public AuthMappingType getType() {
		return type;
	}
	
	public void setType(AuthMappingType type) {
		this.type = type;
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
