package org.gatlin.util.bean.enums;

import org.gatlin.util.bean.IEnum;

/**
 * 操作系统类型
 * 
 * @author lynn
 */
public enum OS implements IEnum {

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
	
	@Override
	public int mark() {
		return mark;
	}
}
