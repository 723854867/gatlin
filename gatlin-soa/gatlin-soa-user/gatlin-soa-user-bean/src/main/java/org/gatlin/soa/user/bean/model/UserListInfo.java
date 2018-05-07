package org.gatlin.soa.user.bean.model;

import java.io.Serializable;

public class UserListInfo implements Serializable {

	private static final long serialVersionUID = 677982987429988079L;

	private long uid;
	private int type;
	private String username;
	private int bindTime;
	private String nickname;
	private int registerTime;
	private String token;
	private Integer os;
	private Integer deviceType;
	private Integer client;
	private Integer loginTime;
	private String avatar;

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

	public int getBindTime() {
		return bindTime;
	}

	public void setBindTime(int bindTime) {
		this.bindTime = bindTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(int registerTime) {
		this.registerTime = registerTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public Integer getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Integer loginTime) {
		this.loginTime = loginTime;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
