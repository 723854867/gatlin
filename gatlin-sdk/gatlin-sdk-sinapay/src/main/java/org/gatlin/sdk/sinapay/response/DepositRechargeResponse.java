package org.gatlin.sdk.sinapay.response;

import org.gatlin.sdk.sinapay.bean.enums.DepositRechargeState;

import com.google.gson.annotations.SerializedName;

public class DepositRechargeResponse extends SinapayResponse {

	private static final long serialVersionUID = 5209352467247173606L;

	@SerializedName("out_trade_no")
	private String outTradeNo;
	@SerializedName("deposit_status")
	private DepositRechargeState state;
	// 如果支付需要推进则会返回此参数，支付推进时需要带上此参数，ticket有效期为15分钟，可以多次使用（最多5次）
	private String ticket;
	// 如果支付方式选择线下支付，则会返回，
	@SerializedName("trans_account_name")
	private String transAccountName;
	// 线下支付收款账号
	@SerializedName("trans_account_no")
	private String transAccountNo;
	// 线下支付收款账号开户行
	@SerializedName("trans_bank_brank")
	private String transBankBrank;
	// 线下支付收款备注
	@SerializedName("trans_trade_no")
	private String transTradeNo;
	// 收银台重定向地址.当请求参数中的"version"的值是"1.1"时，且支付方式扩展是网银并选择"SINAPAY"跳转新浪收银台时，此参数不为空。
	// 商户系统需要将用户按此参数的值重定向到新浪收银台。其他情况不返回此值，"version"的值是"1.0"时也不返回此值。
	@SerializedName("redirect_url")
	private String redirectUrl;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public DepositRechargeState getState() {
		return state;
	}
	
	public void setState(DepositRechargeState state) {
		this.state = state;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTransAccountName() {
		return transAccountName;
	}

	public void setTransAccountName(String transAccountName) {
		this.transAccountName = transAccountName;
	}

	public String getTransAccountNo() {
		return transAccountNo;
	}

	public void setTransAccountNo(String transAccountNo) {
		this.transAccountNo = transAccountNo;
	}

	public String getTransBankBrank() {
		return transBankBrank;
	}

	public void setTransBankBrank(String transBankBrank) {
		this.transBankBrank = transBankBrank;
	}

	public String getTransTradeNo() {
		return transTradeNo;
	}

	public void setTransTradeNo(String transTradeNo) {
		this.transTradeNo = transTradeNo;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
