package org.gatlin.util.bean.enums;

/**
 * 操作系统类型
 * 
 * @author lynn
 */
public enum OS {

	// ios
	IOS(1),
	// 安卓
	ANDROID(2),
	// winphone
	WINPHONE(3),
	// windos系统
	WINDOWS(4),
	// linux系统
	LINUX(5);
	
	private int mark;
	
	private OS(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final OS match(int os) {
		for (OS temp : OS.values()) {
			if (temp.mark == os)
				return temp;
		}
		return null;
	}
}
