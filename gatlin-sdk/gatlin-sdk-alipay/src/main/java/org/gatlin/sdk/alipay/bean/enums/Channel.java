package org.gatlin.sdk.alipay.bean.enums;

public enum Channel {

	// 余额
	BALANCE("balance"),
	// 余额宝
	MONEY_FUND("moneyFund"),
	// 红包
	COUPON("coupon"),
	// 花呗
	PCREDIT("pcredit"),
	// 花呗分期
	PCREDITPAY_INSTALLMENT("pcreditpayInstallment"),
	// 信用卡
	CREDIT_CARD("creditCard"),
	// 信用卡快捷
	CREDIT_CARD_EXPRESS("creditCardExpress"),
	// 信用卡卡通
	CREDIT_CARD_CARTOON("creditCardCartoon"),
	// 信用支付类型(包含信用卡卡通、信用卡快捷、花呗、花呗分期)
	CREDIT_GROUP("credit_group"),
	// 借记卡快捷
	DEBIT_CARD_EXPRESS("debitCardExpress"),
	// 商户预存卡
	MCARD("mcard"),
	// 个人预存卡
	PCARD("pcard"),
	// 优惠(包含实施优惠+商户优惠)
	PROMOTION("promotion"),
	// 营销券
	VOUCHER("voucher"),
	// 积分
	POINT("point"),
	// 商户优惠
	MDISCOUNT("mdiscount"),
	// 网银
	BANK_PAY("bankPay");
	
	private String mark;
	
	private Channel(String mark) {
		this.mark = mark;
	}
	
	public String mark() {
		return mark;
	}
}
