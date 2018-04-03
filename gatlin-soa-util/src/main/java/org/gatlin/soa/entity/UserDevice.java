package org.gatlin.soa.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

/**
 * 用户设备信息:同一个设备只能登陆一个用户
 * <pre>
 * type 和 uid 做唯一索引
 * token 做 unique 索引
 * </pre>
 * 
 * @author lynn
 */
public class UserDevice implements Entity<String> {

	private static final long serialVersionUID = 4745108999226672474L;

	@Id
	private String id;
	private long uid;
	private int type;
	private String token;
	private int created;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String key() {
		return id;
	}
}
