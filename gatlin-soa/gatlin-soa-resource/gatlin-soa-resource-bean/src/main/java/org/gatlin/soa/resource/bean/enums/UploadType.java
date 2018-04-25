package org.gatlin.soa.resource.bean.enums;

public enum UploadType {

	BANNER(1);
	
	private int mark;
	
	private UploadType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
