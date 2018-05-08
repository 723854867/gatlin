package org.gatlin.soa.account.bean.enums;

public enum RechargeState {

	INIT(1),
	SUCCESS(2),
	FAILURE(4);
	
	private int mark;
	
	private RechargeState(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
