package org.gatlin.soa.bean.enums;

public enum WithdrawState {

	INIT(1),
	SUCCESS(2),
	FAILURE(3);
	
	private int mark;
	
	private WithdrawState(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final WithdrawState match(int state) { 
		for (WithdrawState temp : WithdrawState.values()) {
			if (temp.mark == state)
				return temp;
		}
		return null;
	}
}
