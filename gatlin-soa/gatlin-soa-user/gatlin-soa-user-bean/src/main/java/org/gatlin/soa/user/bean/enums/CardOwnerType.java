package org.gatlin.soa.user.bean.enums;

public enum CardOwnerType {

	// 个人
	USER(1),
	// 企业
	COMPANY(2);
	
	private int mark;
	
	private CardOwnerType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
