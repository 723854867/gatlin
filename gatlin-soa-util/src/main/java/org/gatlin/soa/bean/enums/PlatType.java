package org.gatlin.soa.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum PlatType implements IEnum {

	// 阿里支付
	ALIPAY(1),
	// 新浪支付
	SINAPAY(2),
	// 创蓝
	CHUANGLAN(3),
	// 极光推送
	JPUSH(4);
	
	private int mark;
	
	private PlatType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
