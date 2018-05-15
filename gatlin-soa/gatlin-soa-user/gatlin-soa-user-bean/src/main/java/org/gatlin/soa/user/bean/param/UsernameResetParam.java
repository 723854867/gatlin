package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.validate.Validator;

public class UsernameResetParam extends UsernameParam {

	private static final long serialVersionUID = 2366961318097675017L;

	@NotEmpty
	private String captcha;
	@NotEmpty
	private String ncaptcha;
	@NotEmpty
	private String nusername;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getNcaptcha() {
		return ncaptcha;
	}

	public void setNcaptcha(String ncaptcha) {
		this.ncaptcha = ncaptcha;
	}

	public String getNusername() {
		return nusername;
	}
	
	public void setNusername(String nusername) {
		this.nusername = nusername;
	}

	@Override
	public void verify() {
		super.verify();
		switch (getUsernameType()) {
		case EMAIL:
			Assert.isTrue(CoreCode.PARAM_ERR, Validator.isEmail(nusername));
			break;
		case MOBILE:
			Assert.isTrue(CoreCode.PARAM_ERR, PhoneUtil.isMobile(nusername));
			setUsername(PhoneUtil.parseMobile(nusername));
			break;
		default:
			throw new CodeException(CoreCode.PARAM_ERR);
		}
	}
}
