package org.gatlin.soa.user.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

/**
 * 用户账号
 * 
 * <pre>
 * username、type 作为联合唯一索引
 * </pre>
 * 
 * @author lynn
 */
public class Username implements Entity<Long> {

	private static final long serialVersionUID = 8121287853604963144L;

	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private int type;
	private String username;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public Long key() {
		return id;
	}
}
