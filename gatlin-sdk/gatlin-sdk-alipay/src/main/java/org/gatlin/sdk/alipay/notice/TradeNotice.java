package org.gatlin.sdk.alipay.notice;

import javax.validation.constraints.NotEmpty;

public class TradeNotice extends AlipayNotice {

	private static final long serialVersionUID = 1372605764527831328L;

	// 通知的发送时间。格式为yyyy-MM-dd HH:mm:ss
	private String notify_time;
	// 通知的类型
	private String notify_type;
	// 通知校验ID
	private String notify_id;
	// 授权商户的AppId
	private String auth_app_id;
	// 支付宝分配给开发者的应用Id
	private String app_id;
	// 编码格式，如utf-8、gbk、gb2312等
	private String charset;
	// 调用的接口版本，固定为：1.0
	private String version;
	// 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
	private String sign_type;
	private String sign;
	// 支付宝交易凭证号
	private String trade_no;
	// 原支付请求的商户订单号
	@NotEmpty
	private String out_trade_no;
	// 商户业务号，主要是退款通知中返回退款申请的流水号
	private String out_biz_no;
	// 买家支付宝用户号
	private String buyer_id;
	// 买家支付宝账号
	private String buyer_logon_id;
	// 卖家支付宝用户号
	private String seller_id;
	// 卖家支付宝账号
	private String seller_email;
	@NotEmpty
	private String trade_status;
	// 订单金额
	private String total_amount;
	// 实收金额, 商家在交易中实际收到的款项，单位为元
	private String receipt_amount;
	// 开票金额
	private String invoice_amount;
	// 付款金额
	private String buyer_pay_amount;
	// 集分宝金额
	private String point_amount;
	// 总退款金额
	private String refund_fee;
	private String subject;
	private String body;
	private String gmt_create;
	private String gmt_payment;
	private String gmt_refund;
	private String gmt_close;
	// 支付成功的各个渠道金额信息
	private String fund_bill_list;
	private String passback_params;
	// 本交易支付时所使用的所有优惠券信息，详见
	private String voucher_detail_list;

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	
	public String getAuth_app_id() {
		return auth_app_id;
	}
	
	public void setAuth_app_id(String auth_app_id) {
		this.auth_app_id = auth_app_id;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_biz_no() {
		return out_biz_no;
	}

	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getReceipt_amount() {
		return receipt_amount;
	}

	public void setReceipt_amount(String receipt_amount) {
		this.receipt_amount = receipt_amount;
	}

	public String getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public String getBuyer_pay_amount() {
		return buyer_pay_amount;
	}

	public void setBuyer_pay_amount(String buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}

	public String getPoint_amount() {
		return point_amount;
	}

	public void setPoint_amount(String point_amount) {
		this.point_amount = point_amount;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public String getGmt_refund() {
		return gmt_refund;
	}

	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}

	public String getGmt_close() {
		return gmt_close;
	}

	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}

	public String getFund_bill_list() {
		return fund_bill_list;
	}

	public void setFund_bill_list(String fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
	}

	public String getPassback_params() {
		return passback_params;
	}

	public void setPassback_params(String passback_params) {
		this.passback_params = passback_params;
	}

	public String getVoucher_detail_list() {
		return voucher_detail_list;
	}

	public void setVoucher_detail_list(String voucher_detail_list) {
		this.voucher_detail_list = voucher_detail_list;
	}
}
