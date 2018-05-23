package org.gatlin.soa.bean.enums;

public enum PlatType {

	// 阿里支付
	ALIPAY(1),
	// 新浪支付
	SINAPAY(2),
	// 创蓝
	CHUANGLAN(3);
	
	private int mark;
	
	private PlatType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final PlatType match(int type) {
		for (PlatType plat : PlatType.values()) {
			if (plat.mark == type)
				return plat;
		}
		return null;
	}
}
