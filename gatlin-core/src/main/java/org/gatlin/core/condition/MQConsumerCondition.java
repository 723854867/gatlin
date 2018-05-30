package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQConsumerCondition extends GatlinCondition<String> {

	public MQConsumerCondition() {
		super(CoreConsts.MQ_ROLE);
	}

	@Override
	protected boolean checkCondition(String value) {
		String[] roles = value.split(",");
		for (String role : roles) {
			if (role.equalsIgnoreCase("consumer"))
				return true;
		}
		return false;
	}
}
