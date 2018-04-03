package org.gatlin.soa.bean.enums;

/**
 * 设备类型
 * 
 * @author lynn
 */
public enum DeviceType {

	// 个人电脑
	PC(1),
	// 手机
	MOBILE(2),
	// 平板
	TABLET(4);
	
	private int mark;
	
	private DeviceType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final DeviceType match(int type) {
		for (DeviceType temp : DeviceType.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
	
	public static final boolean verify(int mod) {
		int cmod = 0;
		for (DeviceType temp : DeviceType.values())
			cmod |= temp.mark;
		return (cmod & mod) == mod;
	}
}
