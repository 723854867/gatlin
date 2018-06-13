package org.gatlin.soa.account.bean.enums;

import java.util.HashSet;
import java.util.Set;

import org.gatlin.util.bean.IEnum;

public enum RechargeState implements IEnum {

	// 仅仅是本地创建
	INIT(1),
	// 已在第三方支付平台创建订单
	WAIT_PAY(2),
	// 支付成功
	SUCCESS(3),
	// 订单关闭：支付失败或者成功之后已全部退款
	CLOSE(4),
	// 订单关闭，且不可退款
	FINISH(5),
	// 本地超时，只有 INIT 才支持本地超时
	TIMEOUT(6);
	
	private int mark;
	
	private RechargeState(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
	
	public static Set<Integer> repaying() {
		Set<Integer> set = new HashSet<Integer>();
		set.add(INIT.mark);
		set.add(WAIT_PAY.mark);
		return set;
	}
}
