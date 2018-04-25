package org.gatlin.sdk.jiguang.response;

import org.gatlin.core.service.http.HttpResponse;

public class JiGuangResponse implements HttpResponse {

	private static final long serialVersionUID = -3341479919098723428L;
	
	private String msg;
	private String code;

	@Override
	public String code() {
		return code;
	}

	@Override
	public String desc() {
		return msg;
	}

	@Override
	public void verify() {
		if (code.equals("000000"))
			return;
	}
}
