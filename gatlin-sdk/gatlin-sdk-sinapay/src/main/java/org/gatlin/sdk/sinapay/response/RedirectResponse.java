package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class RedirectResponse extends SinapayResponse {

	private static final long serialVersionUID = -294553046838890019L;

	@SerializedName("redirect_url")
	private String redirectUrl;
	
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
