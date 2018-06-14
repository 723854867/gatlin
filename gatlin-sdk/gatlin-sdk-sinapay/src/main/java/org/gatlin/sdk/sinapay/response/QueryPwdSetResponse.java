package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class QueryPwdSetResponse extends SinapayResponse {

	private static final long serialVersionUID = -8435311600538290737L;

	@SerializedName("is_set_paypass")
	private boolean setPwd;
	
	public boolean isSetPwd() {
		return setPwd;
	}
	
	public void setSetPwd(boolean setPwd) {
		this.setPwd = setPwd;
	}
}
