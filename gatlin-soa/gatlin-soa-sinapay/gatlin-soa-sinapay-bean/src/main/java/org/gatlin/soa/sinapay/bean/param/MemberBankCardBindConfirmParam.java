package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaSidParam;

public class MemberBankCardBindConfirmParam extends SoaSidParam {

	private static final long serialVersionUID = 1450225627599237320L;

	@NotEmpty
	private String captcha;
	
	public String getCaptcha() {
		return captcha;
	}
	
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
