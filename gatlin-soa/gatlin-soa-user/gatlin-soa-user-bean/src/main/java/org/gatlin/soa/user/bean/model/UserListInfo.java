package org.gatlin.soa.user.bean.model;

import java.io.Serializable;

import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.bean.enums.OS;

public class UserListInfo implements Serializable {

	private static final long serialVersionUID = 677982987429988079L;

	private OS os;
	private long uid;
	private int bindTime;
	private String token;
	private String avatar;
	private Client client;
	private String username;
	private String nickname;
	private int registerTime;
	private UsernameType type;
	private Integer loginTime;
	private DeviceType deviceType;

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getBindTime() {
		return bindTime;
	}

	public void setBindTime(int bindTime) {
		this.bindTime = bindTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public UsernameType getType() {
		return type;
	}

	public void setType(UsernameType type) {
		this.type = type;
	}

	public Integer getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Integer loginTime) {
		this.loginTime = loginTime;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
}
