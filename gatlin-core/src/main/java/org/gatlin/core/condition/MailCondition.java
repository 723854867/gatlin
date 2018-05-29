package org.gatlin.core.condition;

import org.gatlin.core.CoreConsts;

public class MailCondition extends GatlinCondition<Boolean> {

	protected MailCondition() {
		super(CoreConsts.MAIL_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
