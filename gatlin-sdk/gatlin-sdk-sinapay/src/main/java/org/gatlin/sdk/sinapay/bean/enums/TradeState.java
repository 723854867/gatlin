package org.gatlin.sdk.sinapay.bean.enums;

/**
 * trade_closed、 trade_failed、trade_finished
 * 1、WAIT_PAY -> PAY_FINISHED -> TRADE_FAILED
 * 2、WAIT_PAY -> PAY_FINISHED -> TRADE_FINISHED
 * 3、WAIT_PAY -> TRADE_CLOSED
 * 4、WAIT_PAY -> TRADE_FAILED
 * @author lynn
 */
public enum TradeState {

	// 等待付款
	WAIT_PAY,
	// 已付款
	PAY_FINISHED,
	// 交易失败
	TRADE_FAILED,
	// 交易结束
	TRADE_FINISHED,
	// 交易关闭
	TRADE_CLOSED,
	// 代收冻结成功（只有待收冻结才有）
	PRE_AUTH_APPLY_SUCCESS,
	// 代收撤销成功（只有待收冻结才有）
	PRE_AUTH_CANCELED;
}
