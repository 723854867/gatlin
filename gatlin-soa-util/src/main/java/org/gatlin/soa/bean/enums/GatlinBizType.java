package org.gatlin.soa.bean.enums;

public enum GatlinBizType {

	// 系统调账
	SYSTEM_ADJUST(1),
	// 充值成功
	RECHARGE_SUCCESS(100),
	// 提现
	WITHDRAW(110),
	// 提现成功
	WITHDRAW_SUCCESS(111),
	// 提现失败
	WITHDRAW_FAILURE(112);
	
	private int mark;
	
	private GatlinBizType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
