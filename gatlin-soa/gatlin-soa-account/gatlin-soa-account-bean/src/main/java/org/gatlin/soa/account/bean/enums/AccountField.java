package org.gatlin.soa.account.bean.enums;

public enum AccountField {

	USABLE(1),
	FROZEN(2);
	
	private int mark;
	
	private AccountField(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final AccountField match(int type) {
		for (AccountField field : AccountField.values()) {
			if (field.mark == type)
				return field;
		}
		return null;
	}
}
