package org.gatlin.sdk.sinapay.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum MemberType implements IEnum {

	PERSONAL(1),
	ENTERPRISE(2);
	
	private int mark;
	
	private MemberType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
