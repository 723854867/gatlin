package org.gatlin.sdk.heepay.bean.enums;
/**
 * 账户类型
 * @author fansd
 * @date 2019年3月26日 下午5:41:58
 */
public enum AccountType {

	PRIVATE(0),//对私
	PUBLICK(1);//对公
	
	private int mark;
	
	private AccountType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
	
	public static final AccountType match(int code) {
		for (AccountType temp : AccountType.values()) {
			if (temp.mark== code)
				return temp;
		}
		return null;
	}
}
