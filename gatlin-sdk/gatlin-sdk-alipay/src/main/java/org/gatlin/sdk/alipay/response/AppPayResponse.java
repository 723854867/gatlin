package org.gatlin.sdk.alipay.response;

import com.google.gson.annotations.SerializedName;

public class AppPayResponse extends AlipayResponse {

	private static final long serialVersionUID = -6262205178800523290L;

	// 商户网站唯一订单号
	@SerializedName("out_trade_no")
	private String outTradeNo;
	// 该交易在支付宝系统中的交易流水号
	@SerializedName("trade_no")
	private String tradeNo;
	@SerializedName("total_amount")
	private String totalAmount;
	@SerializedName("seller_id")
	private String sellerId;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
}
