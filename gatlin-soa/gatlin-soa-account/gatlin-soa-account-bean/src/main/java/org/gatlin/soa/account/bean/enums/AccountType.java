package org.gatlin.soa.account.bean.enums;

/**
 * 账户类型
 * 
 * @author lynn
 */
public enum AccountType {

	// 基本账户
	BASIC(1),
	// 积分账户
	INTEGRAL(2),
	// 体验金账户
	EXPERIENCE(4),
	// 融资账户(P2P专有)
	FINANCING(8),
	// 保证金账户
	DEPOSIT(16);
	
	private int mark;
	
	private AccountType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final AccountType match(int mark) {
		for (AccountType temp : AccountType.values()) {
			if (temp.mark == mark)
				return temp;
		}
		return null;
	}
}
