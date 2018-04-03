package org.gatlin.soa.user.bean.enums;

/**
 * 用户名类型
 * 
 * @author lynn
 */
public enum UsernameType {

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
	
	public int mark() {
		return mark;
	}
	
	public static final UsernameType match(int type) {
		for (UsernameType temp : UsernameType.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
