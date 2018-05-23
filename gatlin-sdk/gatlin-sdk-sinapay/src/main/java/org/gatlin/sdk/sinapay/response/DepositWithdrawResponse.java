package org.gatlin.sdk.sinapay.response;

import com.google.gson.annotations.SerializedName;

public class DepositWithdrawResponse extends RedirectResponse {

	private static final long serialVersionUID = 4528574302725460580L;

	@SerializedName("out_trade_no")
	private String outTradeNo;
	@SerializedName("withdraw_status")
	private String withdrawState;
	
	public String getOutTradeNo() {
		return outTradeNo;
	}
	
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	public String getWithdrawState() {
		return withdrawState;
	}
	
	public void setWithdrawState(String withdrawState) {
		this.withdrawState = withdrawState;
	}
}
