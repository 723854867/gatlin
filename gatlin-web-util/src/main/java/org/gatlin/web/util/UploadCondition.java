package org.gatlin.web.util;

import org.gatlin.core.condition.GatlinCondition;
import org.gatlin.web.WebConsts;

public class UploadCondition extends GatlinCondition<Boolean> {

	public UploadCondition() {
		super(WebConsts.Options.UPLOAD_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
