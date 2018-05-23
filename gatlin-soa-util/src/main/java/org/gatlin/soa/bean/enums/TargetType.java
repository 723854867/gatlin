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
	
	public static final TargetType match(int type) { 
		for (TargetType temp : TargetType.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
