package org.gatlin.sdk.sinapay.bean.model;

import java.io.Serializable;

public class ProfitTips implements Serializable {

	private static final long serialVersionUID = 1003761472081506744L;

	// 昨日收益
	private String dayProfit = "0.00";
	// 最近一月收益
	private String monthProfit = "0.00";
	// 总收益
	private String totalProfit = "0.00";

	public String getDayProfit() {
		return dayProfit;
	}

	public void setDayProfit(String dayProfit) {
		this.dayProfit = dayProfit;
	}

	public String getMonthProfit() {
		return monthProfit;
	}

	public void setMonthProfit(String monthProfit) {
		this.monthProfit = monthProfit;
	}

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}
}
