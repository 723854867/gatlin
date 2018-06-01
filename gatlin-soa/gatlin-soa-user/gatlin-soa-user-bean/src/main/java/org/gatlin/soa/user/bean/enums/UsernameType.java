package org.gatlin.soa.user.bean.enums;

import org.gatlin.util.bean.IEnum;

/**
 * 用户名类型
 * 
 * @author lynn
 */
public enum UsernameType implements IEnum {

	// 普通用户名
	COMMON(0),
	// 邮箱用户名
	EMAIL(1),
	// 手机号
	MOBILE(2);
	
	private int mark;
	
	private UsernameType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
