package org.gatlin.core.bean.exceptions;

public class RequestFailException extends GatlinRuntimeException {

	private static final long serialVersionUID = -8215218194929028619L;

	private int code;
	private String desc;
	
	public RequestFailException(Throwable cause) {
		super(cause);
	}
	
	public RequestFailException(int code, String desc) {
		super(desc);
		this.code = code;
		this.desc = desc;
	}
	
	public int code() {
		return code;
	}
	
	public String desc() {
		return desc;
	}
}
