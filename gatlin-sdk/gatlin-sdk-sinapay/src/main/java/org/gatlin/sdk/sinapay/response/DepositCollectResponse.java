package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class DepositCollectResponse extends RedirectResponse {

	private static final long serialVersionUID = -2015800276085562123L;

	@SerializedName("out_trade_no")
	private String outTradeNo;
	@SerializedName("trade_status")
	private String tradeStatus;
	@SerializedName("pay_status")
	private String payStatus;
	private String ticket;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
