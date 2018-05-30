package org.gatlin.soa.bean.model;

import java.io.Serializable;

import org.gatlin.util.DateUtil;

public class Heartbeat implements Serializable {

	private static final long serialVersionUID = 6049335050844817280L;

	private int timestamp;
	private ResourceInfo avatar;
	
	public Heartbeat() {}
	
	public Heartbeat(ResourceInfo avatar) {
		this.avatar = avatar;
		this.timestamp = DateUtil.current();
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public ResourceInfo getAvatar() {
		return avatar;
	}
	
	public void setAvatar(ResourceInfo avatar) {
		this.avatar = avatar;
	}
}
