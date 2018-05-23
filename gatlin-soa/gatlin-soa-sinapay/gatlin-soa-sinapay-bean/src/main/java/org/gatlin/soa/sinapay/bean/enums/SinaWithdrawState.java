package org.gatlin.soa.sinapay.bean.enums;

public enum SinaWithdrawState {

	// 处理代付中
	WAIT_PAY,
	// 钱已到账等待提现确认
	PAYED,
	// 提现代付失败
	PAY_FAILED,
	// 提现中
	WITHDRAWING,
	// 提交新浪支付等待到账
	PROCESSING,
	// 提现成功
	SUCCESS,
	// 提现失败
	FAILED,
	// 失败回收中
	RECALLING,
	// 提现失败已回收
	RECALLED;
}
