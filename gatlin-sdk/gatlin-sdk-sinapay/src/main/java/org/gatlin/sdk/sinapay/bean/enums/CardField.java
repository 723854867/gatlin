package org.gatlin.sdk.sinapay.bean.enums;

public enum CardField {

	CARD_ID(1, "cardId"),
	BANK_CODE(2, "bankCode"),
	BANK_NO(3, "bankNo"),
	ACCOUNT_NAME(4, "accountName"),
	CARD_TYPE(5, "cardType"),
	CARD_ATTRIBUTE(6, "cardAttribute"),
	VERIFY(7, "verify") {
		@Override
		public String process(String value) {
			return value.equals("Y") ? "true" : "false";
		}
	},
	TIME(8, "time"),
	SECURITY(9, "security") {
		@Override
		public String process(String value) {
			return value.equals("Y") ? "true" : "false";
		}
	};
	
	private String mark;
	private int priority;
	
	private CardField(int priority, String mark) {
		this.mark = mark;
		this.priority = priority;
	}
	
	public int priority() {
		return priority;
	}
	
	public String mark() {
		return mark;
	}
	
	public String process(String value) {
		return value;
	}
}
