package org.gatlin.core.bean.exceptions;

public abstract class SdkException extends GatlinRuntimeException {

	private static final long serialVersionUID = 1286775771686496982L;

	protected String code;
	protected String desc;
	
	public SdkException(String code, String desc) {
		super("[" + code + " - " + desc + "]");
		this.code = code;
		this.desc = desc;
	}
	
	public String code() {
		return code;
	}
	
	public String desc() {
		return desc;
	}
}
