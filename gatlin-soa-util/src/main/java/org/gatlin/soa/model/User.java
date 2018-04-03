package org.gatlin.soa.model;

import java.io.Serializable;

import org.gatlin.soa.entity.UserDevice;
import org.gatlin.soa.entity.UserInfo;

public class User implements Serializable {

	private static final long serialVersionUID = -1037327573929081692L;

	private String lockId;
	private UserInfo user;
	private UserDevice device;
	
	public User() {}
	
	public User(UserInfo user, UserDevice device) {
		this(null, user, device);
	}
	
	public User(String lockId, UserInfo user, UserDevice device) {
		this.user = user;
		this.lockId = lockId;
		this.device = device;
	}
	
	public long getId() {
		return user.getId();
	}
	
	public String getLockId() {
		return lockId;
	}
	
	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
	
	public UserInfo getUser() {
		return user;
	}
	
	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	public UserDevice getDevice() {
		return device;
	}
	
	public void setDevice(UserDevice device) {
		this.device = device;
	}
}
