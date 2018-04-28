package org.gatlin.soa.resource.bean.enums;

public enum ResourceType {

	BANNER(1);
	
	private int mark;
	
	private ResourceType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
