package org.gatlin.soa.authority.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.authority.bean.enums.StorageType;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.util.bean.enums.DeviceType;

public class ApiModifyParam extends SoaIdParam {

	private static final long serialVersionUID = 274600132433645878L;

	@NotEmpty
	private String desc;
	private boolean login;
	private int deviceMod;
	private boolean serial;
	@Min(0)
	private int lockTimeout;
	@Min(0)
	private int securityLevel;
	@NotNull
	private StorageType storageType;

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

	public int getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(int securityLevel) {
		this.securityLevel = securityLevel;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	@Override
	public void verify() {
		super.verify();
		int mod = 0;
		for (DeviceType temp : DeviceType.values())
			mod |= temp.mark();
		Assert.isTrue(CoreCode.PARAM_ERR, (mod & deviceMod) == deviceMod);
		if (!login)
			Assert.isTrue(CoreCode.PARAM_ERR, !serial);
		if (!serial)
			Assert.isTrue(CoreCode.PARAM_ERR, 0 == lockTimeout);
	}
}
