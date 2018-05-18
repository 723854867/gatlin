package org.gatlin.sdk.sinapay.response;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class QueryBalanceResponse extends SinapayResponse {

	private static final long serialVersionUID = 4517238932561793337L;

	// 存钱罐收益：昨日收益^最近一月收益^总收益。
	private String bonus;
	// 余额
	private BigDecimal balance;
	// 可用余额
	@SerializedName("available_balance")
	private BigDecimal availableBalance;

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
}
