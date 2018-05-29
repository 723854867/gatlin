package org.gatlin.sdk.sinapay.response;

import org.gatlin.sdk.sinapay.bean.enums.WithdrawState;

import com.google.gson.annotations.SerializedName;

// 此接口将直接代付到收款人之前绑定的银行卡
public class PayToCardResponse extends SinapayResponse {

	private static final long serialVersionUID = -6393520663927233729L;

	@SerializedName("out_trade_no")
	private String outTradeNo;
	@SerializedName("withdraw_status")
	private WithdrawState state;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public WithdrawState getState() {
		return state;
	}

	public void setState(WithdrawState state) {
		this.state = state;
	}
}
