package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQProviderCondition extends GatlinCondition<String> {

	public MQProviderCondition() {
		super(CoreConsts.MQ_ROLE);
	}

	@Override
	protected boolean checkCondition(String value) {
		return value.equalsIgnoreCase("provider") || value.equalsIgnoreCase("all");
	}
}
