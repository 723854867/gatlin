package org.gatlin.util.bean.enums;

import org.gatlin.util.bean.IEnum;

/**
 * 客户端类型
 * 
 * @author lynn
 */
public enum Client implements IEnum {

	// 浏览器
	BROWSER(1),
	// 原生(自定义)
	ORIGINAL(2); 
	
	private int mark;
	
	private Client(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
