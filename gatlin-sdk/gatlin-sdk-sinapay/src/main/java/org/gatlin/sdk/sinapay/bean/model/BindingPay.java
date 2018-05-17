package org.gatlin.sdk.sinapay.bean.model;

public class BindingPay extends PayMethod {

	private static final long serialVersionUID = -6285823486058150663L;
	
	private String cardId;
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public BindingPay() {
		setName("binding_pay");
	}

	@Override
	public String toString() {
		return getName() + "^{0}^" + cardId;
	}
}
