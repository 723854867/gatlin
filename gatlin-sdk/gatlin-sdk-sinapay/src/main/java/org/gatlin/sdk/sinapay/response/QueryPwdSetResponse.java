package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class QueryPwdSetResponse extends SinapayResponse {

	private static final long serialVersionUID = -8435311600538290737L;

	@SerializedName("is_set_paypass")
	private String setPwd;
	
	public String getSetPwd() {
		return setPwd;
	}
	
	public void setSetPwd(String setPwd) {
		this.setPwd = setPwd;
	}
	
	public boolean isPwdSet() {
		return setPwd.equals("Y");
	}
}
