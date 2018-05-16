package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class BankCardBindConfirmResponse extends SinapayResponse {

	private static final long serialVersionUID = 7859254585010174716L;

	@SerializedName("card_id")
	private String cardId;
	@SerializedName("is_verified")
	private String isVerified;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}
}
