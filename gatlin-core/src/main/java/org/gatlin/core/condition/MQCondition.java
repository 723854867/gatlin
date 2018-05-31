package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQCondition extends GatlinCondition<String> {

	public MQCondition() {
		super(CoreConsts.MQ_ROLE);
	}

	@Override
	protected boolean checkCondition(String value) {
		String[] roles = value.split(",");
		boolean provider = false;
		boolean consumer = false;
		for (String role : roles) {
			if (role.equalsIgnoreCase("provider")) 
				provider = true;
			else if (role.equalsIgnoreCase("consumer"))
				consumer = true;
		}
		return provider || consumer;
	}
}
