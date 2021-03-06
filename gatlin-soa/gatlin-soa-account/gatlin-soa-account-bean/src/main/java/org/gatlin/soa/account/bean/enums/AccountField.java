package org.gatlin.soa.account.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum AccountField implements IEnum {

	USABLE(1),
	FROZEN(2);
	
	private int mark;
	
	private AccountField(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
