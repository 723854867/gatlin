package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.util.PhoneUtil;

public class RegisterParam extends SoaParam {

	private static final long serialVersionUID = 4433642237759495470L;

	@Min(1)
	private Long inviter;
	// 验证码
	private String captcha;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotNull
	private UsernameType usernameType;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsernameType getUsernameType() {
		return usernameType;
	}

	public void setUsernameType(UsernameType usernameType) {
		this.usernameType = usernameType;
	}

	@Override
	public void verify() {
		super.verify();
		switch (usernameType) {
		case EMAIL:
			Assert.hasText(captcha, CoreCode.PARAM_ERR);
			break;
		case MOBILE:
			Assert.hasText(captcha, CoreCode.PARAM_ERR);
			this.username = PhoneUtil.parseMobile(username);
			break;
		default:
			break;
		}
	}
}
