package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;

public class RegisterParam extends UsernameParam {

	private static final long serialVersionUID = 4433642237759495470L;

	@Min(1)
	private Long inviter;
	// 验证码
	private String captcha;
	@NotEmpty
	private String password;

	public Long getInviter() {
		return inviter;
	}

	public void setInviter(Long inviter) {
		this.inviter = inviter;
	}

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
	
	@Override
	public void verify() {
		super.verify();
		switch (getUsernameType()) {
		case EMAIL:
		case MOBILE:
			Assert.hasText(CoreCode.PARAM_ERR, captcha);
			break;
		default:
			break;
		}
	}
}
