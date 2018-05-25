package org.gatlin.sdk.jpush.response;

import org.gatlin.core.service.http.HttpResponse;

import com.google.gson.annotations.SerializedName;

public class JPushResponse implements HttpResponse {

	private static final long serialVersionUID = -3341479919098723428L;
	
	private String sendno;
	@SerializedName("msg_id")
	private String msgId;
	private Integer statusCode;
	
	public String getSendno() {
		return sendno;
	}
	
	public String getMsgId() {
		return msgId;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}

	@Override
	public String code() {
		return null;
	}

	@Override
	public String desc() {
		return null;
	}

	@Override
	public void verify() {}
}
