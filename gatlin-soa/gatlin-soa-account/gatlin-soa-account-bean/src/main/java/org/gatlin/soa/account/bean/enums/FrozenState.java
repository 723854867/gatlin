package org.gatlin.soa.account.bean.enums;

public enum FrozenState {

	FREEZING(1),
	THAW(2);
	
	private int mark;
	
	private FrozenState(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
