package org.gatlin.sdk.sinapay.bean.enums;

public enum MemberType {

	PERSONAL(1),
	ENTERPRISE(2);
	
	private int mark;
	
	private MemberType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
