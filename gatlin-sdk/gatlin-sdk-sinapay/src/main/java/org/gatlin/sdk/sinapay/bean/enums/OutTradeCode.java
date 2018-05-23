package org.gatlin.sdk.sinapay.bean.enums;

public enum OutTradeCode {

	COLLECT_OTHER("1000"),
	// 代收投资金
	COLLECT_INVEST("1001"),
	COLLECT_REPAY("1002"),
	PAY_OTHER("2000"),
	// 代付借款金
	PAY_LOAN("2001"),
	// 代付（本金/收益）金
	PAY_PRFIT("2002");
	
	private String mark;
	
	private OutTradeCode(String mark) {
		this.mark = mark;
	}
	
	public String mark() {
		return mark;
	}
	
	public static final OutTradeCode match(String code) {
		for (OutTradeCode temp : OutTradeCode.values()) {
			if (temp.mark.equals(code))
				return temp;
		}
		return null;
	}
}
