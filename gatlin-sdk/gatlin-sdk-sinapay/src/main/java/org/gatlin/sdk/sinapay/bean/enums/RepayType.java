package org.gatlin.sdk.sinapay.bean.enums;

/**
 * 还款方式
 * 
 * @author lynn
 */
public enum RepayType {

	// 一次性还本付息
	REPAY_CAPITAL_WITH_INTEREST,
	// 等额本金
	AVERAGE_CAPITAL,
	// 等额本息
	AVERAGE_CAPITAL_PLUS_INTERES,
	// 按期付息到期还本
	SCHEDULED_INTEREST_PAYMENTS_DUE,
	// 其他
	OTHER;
}
