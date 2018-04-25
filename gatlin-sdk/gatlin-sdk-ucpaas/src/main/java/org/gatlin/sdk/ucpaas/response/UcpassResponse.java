package org.gatlin.sdk.ucpaas.response;

import org.gatlin.core.service.http.HttpResponse;
import org.gatlin.sdk.ucpaas.UcpassException;

public class UcpassResponse implements HttpResponse {

	private static final long serialVersionUID = -5321643242618427081L;
	
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
		throw new UcpassException(code, msg);
	}
}
