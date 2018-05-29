package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQConsumerCondition extends GatlinCondition<String> {

	public MQConsumerCondition() {
		super(CoreConsts.ACTIVEMQ_TYPE);
	}

	@Override
	protected boolean checkCondition(String value) {
		return value.equalsIgnoreCase("consumer");
	}
}
