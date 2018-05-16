package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class BankCardBindResponse extends SinapayResponse {

	private static final long serialVersionUID = -158832686129303135L;

	@SerializedName("card_id")
	private String cardId;
	@SerializedName("is_verified")
	private String isVerified;
}
