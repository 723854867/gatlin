package org.gatlin.soa.sinapay.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.model.ProfitTips;
import org.gatlin.sdk.sinapay.response.QueryBalanceResponse;

public class BalanceInfo implements Serializable {

	private static final long serialVersionUID = 6271145564501321076L;

	// 余额
	private BigDecimal total;
	// 可用余额
	private BigDecimal usable;
	private ProfitTips profit;
	
	public BalanceInfo() {}
	
	public BalanceInfo(QueryBalanceResponse response) {
		this.profit = response.profit();
		this.total = response.getBalance();
		this.usable = response.getAvailableBalance();
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getUsable() {
		return usable;
	}

	public void setUsable(BigDecimal usable) {
		this.usable = usable;
	}

	public ProfitTips getProfit() {
		return profit;
	}

	public void setProfit(ProfitTips profit) {
		this.profit = profit;
	}

}
