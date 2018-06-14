package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.validate.Mobile;

public class BankCardMobileModifyParam extends SoaParam {

	private static final long serialVersionUID = -2611325419317811385L;

	@NotEmpty
	private String cardId;
	@Mobile
	@NotEmpty
	private String mobile;
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public void verify() {
		super.verify();
		this.mobile = PhoneUtil.parseMobile(this.mobile);
	}
}
