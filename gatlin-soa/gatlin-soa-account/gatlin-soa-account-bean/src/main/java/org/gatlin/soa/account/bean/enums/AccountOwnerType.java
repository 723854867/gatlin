package org.gatlin.soa.account.bean.enums;

/**
 * 账号归属类型
 * 
 * @author lynn
 */
public enum AccountOwnerType {

	// 个人账户
	USER(1),
	// 平台账户
	PLAT(2),
	// 企业账户
	COMPANY(3);
	
	private int mark;
	
	private AccountOwnerType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
