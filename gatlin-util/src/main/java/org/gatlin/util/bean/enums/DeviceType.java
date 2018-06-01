package org.gatlin.util.bean.enums;

import org.gatlin.util.bean.IEnum;

/**
 * 设备类型
 * 
 * @author lynn
 */
public enum DeviceType implements IEnum {

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
	
	@Override
	public int mark() {
		return mark;
	}
	
	public abstract boolean support(OS os);
	
	public static final boolean verify(int mod) {
		int cmod = 0;
		for (DeviceType temp : DeviceType.values())
			cmod |= temp.mark;
		return (cmod & mod) == mod;
	}
}
