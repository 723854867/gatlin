package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaParam;

public class UserListParam extends SoaParam {

	private static final long serialVersionUID = -329043992169770469L;

	private Integer os;
	private Integer uid;
	private Integer client;
	private String nickname;
	private String username;
	private Integer deviceType;
	private Integer usernameType;
	private Integer registerTimeEnd;
	private Integer registerTimeBegin;

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getUsernameType() {
		return usernameType;
	}

	public void setUsernameType(Integer usernameType) {
		this.usernameType = usernameType;
	}

	public Integer getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(Integer registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

	public Integer getRegisterTimeBegin() {
		return registerTimeBegin;
	}

	public void setRegisterTimeBegin(Integer registerTimeBegin) {
		this.registerTimeBegin = registerTimeBegin;
	}
}
