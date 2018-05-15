package org.gatlin.web;

import org.gatlin.core.condition.GatlinCondition;

public class SinapayCondition extends GatlinCondition<Boolean> {

	protected SinapayCondition() {
		super(WebConsts.Options.SINAPAY_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
