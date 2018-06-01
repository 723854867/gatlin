package org.gatlin.soa.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum TargetType implements IEnum {

	USER(1),
	COMPANY(2),
	PLATFORM(3);
	
	private int mark;
	
	private TargetType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
