package org.gatlin.util.bean.enums;

/**
 * 设备类型
 * 
 * @author lynn
 */
public enum DeviceType {

	// 个人电脑
	PC(1) {
		@Override
		public boolean support(OS os) {
			return os == OS.WINDOWS || os == OS.LINUX;
		}
	},
	// 手机
	MOBILE(2) {
		@Override
		public boolean support(OS os) {
			return os == OS.IOS || os == OS.ANDROID || os == OS.WINPHONE;
		}
	},
	// 平板
	TABLET(4) {
		@Override
		public boolean support(OS os) {
			return os == OS.IOS || os == OS.ANDROID || os == OS.WINDOWS;
		}
	};
	
	private int mark;
	
	private DeviceType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public abstract boolean support(OS os);
	
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
