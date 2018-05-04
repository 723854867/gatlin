package org.gatlin.soa.authority.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.core.bean.Entity;

public class CfgApi implements Entity<Integer> {

	private static final long serialVersionUID = -1477077842037120704L;

	@Id
	@GeneratedValue
	private int id;
	private String path;
	private String desc;
	private boolean login;
	private int deviceMod;
	private boolean serial;
	private int lockTimeout;
	private String storageType;
	// 安全级别：和服务器的当前状态级别对应，只有级别高于服务器的状态级别才可以调用
	private int securityLevel;
	private int created;
	private int updated;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

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
	
	public int getSecurityLevel() {
		return securityLevel;
	}
	
	public void setSecurityLevel(int securityLevel) {
		this.securityLevel = securityLevel;
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
	public Integer key() {
		return this.id;
	}
}
