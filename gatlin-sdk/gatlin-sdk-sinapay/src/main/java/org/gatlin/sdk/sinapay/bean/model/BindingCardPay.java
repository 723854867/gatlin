package org.gatlin.sdk.sinapay.bean.model;

import java.text.MessageFormat;

import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;

public class BindingCardPay extends PayMethod {

	private static final long serialVersionUID = 6043616684054998585L;
	
	private static final String FORMAT = "{0}^{1},{2},{3}";
	
	private String sinaId;
	private String cardId;
	private MemberIdentityType identityType = MemberIdentityType.UID;
	
	public BindingCardPay() {
		setName("binding_card");
	}

	public String getSinaId() {
		return sinaId;
	}
	
	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public MemberIdentityType getIdentityType() {
		return identityType;
	}
	
	public void setIdentityType(MemberIdentityType identityType) {
		this.identityType = identityType;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format(FORMAT, getName(), sinaId, identityType.name(), cardId);
	}
}
