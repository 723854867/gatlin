package org.gatlin.soa.sinapay.bean.enums;

public enum RechargeState {

	// 处理中
	PROCESSING,
	// 充值失败
	FAILED,
	// 钱已到账待扣回
	WAIT_RECALL,
	// 正在扣回
	RECALLING,
	// 成功(充值成功或者钱已口回到资金池)
	SUCCESS;
}
