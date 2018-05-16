package org.gatlin.soa.user.bean.enums;

public enum UserMod {

	// 用户已存在
	EXIST(1),
	// 未设置密码
	PWD_UNSET(2),
	// 开启短信通知
	SMS_ON(4),
	// 开启推送通知
	PUSH_ON(8);
	
	private static final int MOD = SMS_ON.mark() | PUSH_ON.mark();
	
	private int mark;
	
	private UserMod(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static boolean checkModify(int mod) {
		return (MOD & mod) == mod;
	}
}
