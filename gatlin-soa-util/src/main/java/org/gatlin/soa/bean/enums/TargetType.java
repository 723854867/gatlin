package org.gatlin.soa.bean.enums;

public enum TargetType {

	USER(1),
	COMPANY(2),
	PLATFORM(3);
	
	private int mark;
	
	private TargetType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
