package org.gatlin.soa.account.bean.enums;

import org.gatlin.util.bean.IEnum;

/**
 * 账户类型
 * 
 * @author lynn
 */
public enum AccountType implements IEnum {

	// 基本账户
	BASIC(1),
	// 积分账户
	SCORE(2),
	// 体验金账户
	EXP(4),
	// 融资账户(P2P专有)
	FINANCE(8),
	// 保证金账户
	DEPOSIT(16);
	
	private int mark;
	
	private AccountType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
