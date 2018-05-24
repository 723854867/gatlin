package org.gatlin.sdk.jisu;

import org.gatlin.core.service.http.HttpResponse;
import org.gatlin.sdk.jisu.bean.JisuException;

public class JisuResponse<RESULT> implements HttpResponse  {

	private static final long serialVersionUID = 6826535204575467830L;
	
	private String msg;
	private String status;
	private RESULT result;

	@Override
	public String code() {
		return status;
	}

	@Override
	public String desc() {
		return msg;
	}
	
	public RESULT getResult() {
		return result;
	}
	
	public void setResult(RESULT result) {
		this.result = result;
	}
	
	@Override
	public void verify() {
		if (!status.equals("0"))
			throw new JisuException(status, msg);
	}
}
