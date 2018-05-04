package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;

public class PwdResetParam extends UsernameParam {

	private static final long serialVersionUID = -7233153340246412627L;

	@NotEmpty
	private String pwd;
	private String opwd;
	private String captcha;
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getOpwd() {
		return opwd;
	}
	
	public void setOpwd(String opwd) {
		this.opwd = opwd;
	}
	
	public String getCaptcha() {
		return captcha;
	}
	
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	@Override
	public void verify() {
		super.verify();
		switch (getUsernameType()) {
		case COMMON:
			Assert.hasText(CoreCode.PARAM_ERR, opwd);
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
