package org.gatlin.sdk.sinapay.response;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class QueryTradeRelatedResponse extends SinapayResponse {

	private static final long serialVersionUID = 5993481733431356176L;

	@SerializedName("inner_trade_related_no")
	private String innerTradeRelatedNo;
	private BigDecimal balance;
	@SerializedName("extend_param")
	private String extendParam;

	public String getInnerTradeRelatedNo() {
		return innerTradeRelatedNo;
	}

	public void setInnerTradeRelatedNo(String innerTradeRelatedNo) {
		this.innerTradeRelatedNo = innerTradeRelatedNo;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getExtendParam() {
		return extendParam;
	}

	public void setExtendParam(String extendParam) {
		this.extendParam = extendParam;
	}
}
