package org.gatlin.soa.user.bean.entity;

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
	private String token;
	private long uid;
	private int os;
	private int type;
	private int client;
	private int created;
	

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
	
	public int getOs() {
		return os;
	}
	
	public void setOs(int os) {
		this.os = os;
	}
	
	public int getClient() {
		return client;
	}
	
	public void setClient(int client) {
		this.client = client;
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
		return this.token;
	}
}
