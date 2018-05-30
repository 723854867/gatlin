package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MQProviderCondition extends GatlinCondition<String> {

	public MQProviderCondition() {
		super(CoreConsts.MQ_ROLE);
	}

	@Override
	protected boolean checkCondition(String value) {
		String[] roles = value.split(",");
		for (String role : roles) {
			if (role.equalsIgnoreCase("provider"))
				return true;
		}
		return false;
	}
}
