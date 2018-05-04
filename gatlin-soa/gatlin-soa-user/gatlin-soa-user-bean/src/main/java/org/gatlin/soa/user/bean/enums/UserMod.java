package org.gatlin.soa.user.bean.enums;

public enum UserMod {

	// 用户已存在
	EXIST(1);
	
	private int mark;
	
	private UserMod(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
