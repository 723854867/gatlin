package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.util.PhoneUtil;

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
			Assert.hasText(captcha, CoreCode.PARAM_ERR);
			break;
		case MOBILE:
			Assert.hasText(captcha, CoreCode.PARAM_ERR);
			setUsername(PhoneUtil.parseMobile(getUsername()));
			break;
		default:
			break;
		}
	}
}
