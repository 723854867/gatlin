package org.gatlin.soa.user.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum EmployeeState implements IEnum {
	
	NORMAL(1),
	LEAVE(2);
	
	private int mark;
	
	private EmployeeState(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return this.mark;
	}
}
