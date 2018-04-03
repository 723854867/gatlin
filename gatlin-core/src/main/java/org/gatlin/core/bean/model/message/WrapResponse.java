package org.gatlin.core.bean.model.message;

import java.io.Serializable;

public class WrapResponse implements Serializable {

	private static final long serialVersionUID = 7702447696105493376L;

	private Object result;
	
	public WrapResponse() {}
	
	public WrapResponse(Object result) {
		this.result = result;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
}
