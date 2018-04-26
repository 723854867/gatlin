package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.bean.enums.OS;

public class LoginParam extends UsernameParam {

	private static final long serialVersionUID = -1393766562081282538L;

	@NotNull
	private OS os;
	@NotNull
	private Client client;
	@NotEmpty
	private String password;
	@NotNull
	private DeviceType deviceType;

	public OS getOs() {
		return os;
	}
	
	public void setOs(OS os) {
		this.os = os;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	
	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(CoreCode.PARAM_ERR, deviceType.support(os));
	}
}
