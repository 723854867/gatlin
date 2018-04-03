package org.gatlin.core.bean.exceptions;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.code.Code;

public class CodeException extends GatlinRuntimeException {

	private static final long serialVersionUID = -7056952516554371288L;

	private Code code;
	private String desc;
	
	public CodeException() {
		this(CoreCode.SYSTEM_ERR);
	}
	
	public CodeException(Code code) {
		super(code.defaultValue());
		this.code = code;
	}
	
	public CodeException(String desc) {
		this(CoreCode.SYSTEM_ERR, desc);
	}
	
	public CodeException(Throwable cause) {
		this(CoreCode.SYSTEM_ERR, cause);
	}
	
	public CodeException(Code code, String desc) {
		super(code.defaultValue() + " - [" + desc + "]");
		this.code = code;
		this.desc = desc;
	}
	
	public CodeException(Code code, Throwable cause) {
		super(code.defaultValue(), cause);
		this.code = code;
	}
	
	public Code code() {
		return code;
	}
	
	public String desc() {
		return desc;
	}
}
