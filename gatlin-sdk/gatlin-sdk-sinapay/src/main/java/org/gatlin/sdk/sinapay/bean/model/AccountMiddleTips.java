package org.gatlin.sdk.sinapay.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;

public class AccountMiddleTips implements Serializable {

	private static final long serialVersionUID = -5017993851773995977L;

	// 新浪账户号
	private String account;
	// 余额
	private BigDecimal amount;
	// 外部业务码
	private OutTradeCode tradeCode;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public OutTradeCode getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(OutTradeCode tradeCode) {
		this.tradeCode = tradeCode;
	}
}
