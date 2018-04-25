package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class HttpCondition extends GatlinCondition<Boolean> {

	public HttpCondition() {
		super(CoreConsts.Options.HTTP_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
