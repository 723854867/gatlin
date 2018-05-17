package org.gatlin.sdk.sinapay.bean.enums;

public enum DepositRechargeState {

	// 成功(系统会异步通知)
	SUCCESS,
	// 失败(系统会异步通知)
	FAILED,
	// 处理中(系统不会异步通知)
	PROCESSING;
}
