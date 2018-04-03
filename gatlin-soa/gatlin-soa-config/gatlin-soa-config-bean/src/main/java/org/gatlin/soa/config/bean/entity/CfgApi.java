package org.gatlin.soa.config.bean.entity;

import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class CfgApi implements Entity<String> {

	private static final long serialVersionUID = -1477077842037120704L;

	@Id
	private String path;
	private String desc;
	private boolean login;
	private int deviceMod;
	private boolean serial;
	private int lockTimeout;
	private String storageType;
	private int created;
	private int updated;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean isLogin() {
		return login;
	}
	
	public void setLogin(boolean login) {
		this.login = login;
	}
	
	public int getDeviceMod() {
		return deviceMod;
	}
	
	public void setDeviceMod(int deviceMod) {
		this.deviceMod = deviceMod;
	}
	
	public boolean isSerial() {
		return serial;
	}
	
	public void setSerial(boolean serial) {
		this.serial = serial;
	}
	
	public int getLockTimeout() {
		return lockTimeout;
	}
	
	public void setLockTimeout(int lockTimeout) {
		this.lockTimeout = lockTimeout;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public String key() {
		return this.path;
	}
}
