package org.gatlin.sdk.sinapay.bean.model;

import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CardType;

public class OnlineBankPay extends PayMethod {

	private static final long serialVersionUID = -3684188228586910189L;

	private String bankCode = "SINAPAY";
	private String cardType = "DEBIT";
	private String cardAttribute = "C";

	public OnlineBankPay() {
		setName("online_bank");
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType.name();
	}

	public String getCardAttribute() {
		return cardAttribute;
	}

	public void setCardAttribute(CardAttribute cardAttribute) {
		this.cardAttribute = cardAttribute.name();
	}

	@Override
	public String toString() {
		return getName() + "^{0}^" + bankCode + "," + cardType + "," + cardAttribute;
	}
}
