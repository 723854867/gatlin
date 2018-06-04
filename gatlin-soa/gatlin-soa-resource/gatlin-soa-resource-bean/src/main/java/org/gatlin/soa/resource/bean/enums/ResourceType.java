package org.gatlin.soa.resource.bean.enums;

public enum ResourceType {

	// 轮播图
	BANNER(1),
	// 用户头像
	AVATAR(50),
	// 用户默认头像
	AVATAR_DEFAULT(51),
	// 银行icon
	BANK_ICON(60);
	
	private int mark;
	
	private ResourceType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
