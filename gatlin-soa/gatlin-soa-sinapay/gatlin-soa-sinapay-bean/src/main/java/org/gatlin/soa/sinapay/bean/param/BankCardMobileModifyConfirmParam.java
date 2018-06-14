package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.util.PhoneUtil;
import org.gatlin.util.validate.Mobile;

public class BankCardMobileModifyConfirmParam extends BankCardConfirmParam {

	private static final long serialVersionUID = -7825034608200012240L;

	@Mobile
	@NotEmpty
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public void verify() {
		super.verify();
		this.mobile = PhoneUtil.parseMobile(mobile);
	}
}
