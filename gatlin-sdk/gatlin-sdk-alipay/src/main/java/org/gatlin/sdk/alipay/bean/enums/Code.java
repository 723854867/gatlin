package org.gatlin.sdk.alipay.bean.enums;

public enum Code {

	SUCCESS("10000"),
	SERVER_UNAVAILABLE("20000"),
	AUTH_FAILURE("20001"),
	PARAM_ERROR("40001"),
	PARAM_ILLEGAL("40002"),
	PROCESS_FAILURE("40004"),
	PRIVILLEGE_LIMIT("40006");
	
	private String mark;
	
	private Code(String mark) {
		this.mark = mark;
	}
	
	public String mark() {
		return mark;
	}
	
	public static final Code match(String code) {
		for (Code temp : Code.values()) {
			if (temp.mark.equals(code))
				return temp;
		}
		return null;
	}
}
