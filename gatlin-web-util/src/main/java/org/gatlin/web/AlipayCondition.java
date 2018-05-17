package org.gatlin.web;

import org.gatlin.core.condition.GatlinCondition;

public class AlipayCondition extends GatlinCondition<Boolean> {

	public AlipayCondition() {
		super(WebConsts.Options.ALIPAY_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}