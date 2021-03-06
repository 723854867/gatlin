package org.gatlin.sdk.sinapay.bean.enums;

public enum WithdrawState {

	// 初始化（新浪支付安全模式订单状态）
	INIT,
	// 成功(系统会异步通知)
	SUCCESS,
	// 失败(系统会异步通知)
	FAILED,
	// 处理中(系统不会异步通知)
	PROCESSING,
	// 退票(系统会异步通知)
	RETURNT_TICKET;
}
