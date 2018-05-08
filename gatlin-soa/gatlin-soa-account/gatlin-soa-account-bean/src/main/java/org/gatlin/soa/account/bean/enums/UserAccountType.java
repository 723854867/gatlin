package org.gatlin.soa.account.bean.enums;

public enum UserAccountType {

	// 基本账户
	BASIC(1),
	// 积分账户
	INTEGRAL(2),
	// 体验金账户
	EXPERIENCE(4),
	// 融资账户(P2P专有)
	FINANCING(8);
	
	private int mark;
	
	private UserAccountType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final UserAccountType match(int mark) {
		for (UserAccountType temp : UserAccountType.values()) {
			if (temp.mark == mark)
				return temp;
		}
		return null;
	}
}
