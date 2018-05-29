package org.gatlin.core.bean.model.option;

import java.math.BigDecimal;

public class DecimalOption extends Option<BigDecimal> {

	private static final long serialVersionUID = 5370903654461268657L;

	public DecimalOption() {}

	public DecimalOption(String key) {
		super(key);
	}
	
	public DecimalOption(String key, BigDecimal defaultValue) {
		super(key, defaultValue);
	}
}
