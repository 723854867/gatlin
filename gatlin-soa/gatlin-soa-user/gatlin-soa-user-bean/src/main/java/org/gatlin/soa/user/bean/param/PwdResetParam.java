package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;

public class PwdResetParam extends UsernameParam {

	private static final long serialVersionUID = -7233153340246412627L;

	private String captcha;
	@NotEmpty
	private String password;
	private String opassword;
	
	public String getCaptcha() {
		return captcha;
	}
	
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getOpassword() {
		return opassword;
	}
	
	public void setOpassword(String opassword) {
		this.opassword = opassword;
	}
	
	@Override
	public void verify() {
		super.verify();
		switch (getUsernameType()) {
		case COMMON:
			Assert.hasText(CoreCode.PARAM_ERR, opassword);
			break;
		case EMAIL:
		case MOBILE:
			Assert.hasText(CoreCode.PARAM_ERR, captcha);
			break;
		default:
			break;
		}
	}
}
