package org.gatlin.sdk.sinapay.bean.enums;

public enum ProfitField {
	
	DAY_PROFIT(1, "dayProfit"),
	MONTH_PROFIT(2, "monthProfit"),
	TOTAL_PROFIT(3, "totalProfit");
	
	private String mark;
	private int priority;
	
	private ProfitField(int priority, String mark) {
		this.mark = mark;
		this.priority = priority;
	}
	
	public int priority() {
		return priority;
	}
	
	public String mark() {
		return mark;
	}
}
