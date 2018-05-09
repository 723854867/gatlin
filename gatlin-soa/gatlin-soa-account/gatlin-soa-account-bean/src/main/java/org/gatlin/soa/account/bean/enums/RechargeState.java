package org.gatlin.soa.account.bean.enums;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;

public enum RechargeState {

	// 仅仅是本地创建
	INIT(1) {
		@Override
		public String machineName() {
			return "rechargeInitStateMachine";
		}
	},
	// 已在第三方支付平台创建订单
	WAIT_PAY(2) {
		@Override
		public String machineName() {
			return "rechargeWaitPayStateMachine";
		}
	},
	// 支付成功
	SUCCESS(2) {
		@Override
		public String machineName() {
			return "rechargeSuccessStateMachine";
		}
	},
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
	
	public int mark() {
		return mark;
	}
	
	public String machineName() {
		throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
	}
	
	public static final RechargeState match(int mark) { 
		for (RechargeState state : RechargeState.values()) {
			if (state.mark == mark)
				return state;
		}
		return null;
	}
}
