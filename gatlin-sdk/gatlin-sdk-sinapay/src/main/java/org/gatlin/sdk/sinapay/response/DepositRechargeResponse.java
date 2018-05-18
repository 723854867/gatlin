package org.gatlin.sdk.sinapay.response;

import org.gatlin.sdk.sinapay.bean.enums.DepositRechargeState;

import com.google.gson.annotations.SerializedName;

public class DepositRechargeResponse extends RedirectResponse {

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
}
