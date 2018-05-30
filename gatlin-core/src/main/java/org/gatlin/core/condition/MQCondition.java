package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQCondition extends GatlinCondition<String> {

	public MQCondition() {
		super(CoreConsts.MQ_ROLE);
	}

	@Override
	protected boolean checkCondition(String value) {
		return value.equalsIgnoreCase("provider") || value.equalsIgnoreCase("consumer") || value.equalsIgnoreCase("all");
	}
}
