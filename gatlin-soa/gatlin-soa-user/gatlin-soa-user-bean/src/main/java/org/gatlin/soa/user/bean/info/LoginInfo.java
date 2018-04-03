package org.gatlin.soa.user.bean.info;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = -7009358941475299177L;

	private long uid;
	private String token;
	
	public LoginInfo() {}
	
	public LoginInfo(long uid, String token) {
		this.uid = uid;
		this.token = token;
	}
	
	public long getUid() {
		return uid;
	}
	
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
