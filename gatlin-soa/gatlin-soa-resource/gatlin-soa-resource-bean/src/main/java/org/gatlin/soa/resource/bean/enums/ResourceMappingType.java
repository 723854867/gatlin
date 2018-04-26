package org.gatlin.soa.resource.bean.enums;

public enum ResourceMappingType {

	UPLOAD(1),
	RESOURCE(2);
	
	private int mark;
	
	private ResourceMappingType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
