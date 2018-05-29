package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQProviderCondition extends GatlinCondition<String> {

	public MQProviderCondition() {
		super(CoreConsts.ACTIVEMQ_TYPE);
	}

	@Override
	protected boolean checkCondition(String value) {
		return value.equalsIgnoreCase("provider");
	}
}
