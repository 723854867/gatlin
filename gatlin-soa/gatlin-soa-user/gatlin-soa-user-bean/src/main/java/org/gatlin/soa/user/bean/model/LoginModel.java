package org.gatlin.soa.user.bean.model;

import java.io.Serializable;

import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.info.LoginInfo;

public class LoginModel implements Serializable {

	private static final long serialVersionUID = 2496770867664264412L;

	private LoginInfo info;
	private UserDevice ndevice;
	private UserDevice odevice;
	
	public LoginModel() {}
	
	public LoginModel(LoginInfo info, UserDevice ndevice, UserDevice odevice) {
		this.info = info;
		this.ndevice = ndevice;
		this.odevice = odevice;
	}

	public LoginInfo getInfo() {
		return info;
	}

	public void setInfo(LoginInfo info) {
		this.info = info;
	}

	public UserDevice getNdevice() {
		return ndevice;
	}

	public void setNdevice(UserDevice ndevice) {
		this.ndevice = ndevice;
	}

	public UserDevice getOdevice() {
		return odevice;
	}
	
	public void setOdevice(UserDevice odevice) {
		this.odevice = odevice;
	}
}
