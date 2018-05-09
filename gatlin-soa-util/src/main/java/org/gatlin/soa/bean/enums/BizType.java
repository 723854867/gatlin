package org.gatlin.soa.bean.enums;

public enum BizType {

	// 充值成功
	RECHARGE_SUCCESS(100);
	
	private int mark;
	
	private BizType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
