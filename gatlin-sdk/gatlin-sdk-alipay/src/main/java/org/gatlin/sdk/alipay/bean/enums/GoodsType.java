package org.gatlin.sdk.alipay.bean.enums;

public enum GoodsType {

	VIRTUAL(0),
	OBJECT(1);
	
	private int mark;
	
	private GoodsType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
