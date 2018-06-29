package org.gatlin.sdk.amazon.bean.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class Currency extends HashMap<String, String> {

	private static final long serialVersionUID = 5314341884627511034L;

	// ISO 4217 格式的三位数货币代码
	public Currency code(String code) {
		this.put("CurrencyCode", code);
		return this;
	}
	
	// 货币金额
	public Currency value(BigDecimal value) {
		this.put("Value", value.toString());
		return this;
	}
}
