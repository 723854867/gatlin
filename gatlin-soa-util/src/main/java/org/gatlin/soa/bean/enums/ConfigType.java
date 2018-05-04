package org.gatlin.soa.bean.enums;

public enum ConfigType {

	COURIER(1),
	RESOURCE(2),
	ACCOUNT(3);
	
	private int mark;
	
	private ConfigType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
