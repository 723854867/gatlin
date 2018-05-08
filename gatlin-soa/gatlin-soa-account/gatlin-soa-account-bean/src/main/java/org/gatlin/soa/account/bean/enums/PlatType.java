package org.gatlin.soa.account.bean.enums;

public enum PlatType {

	ALIPAY(1);
	
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
