package org.gatlin.soa.account.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum WithdrawState implements IEnum {

	INIT(1),
	SUCCESS(2),
	FAILURE(3);
	
	private int mark;
	
	private WithdrawState(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
