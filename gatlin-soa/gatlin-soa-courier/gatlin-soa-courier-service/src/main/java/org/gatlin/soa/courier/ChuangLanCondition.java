package org.gatlin.soa.courier;

import org.gatlin.core.condition.GatlinCondition;

public class ChuangLanCondition extends GatlinCondition<Boolean> {

	public ChuangLanCondition() {
		super(CourierConsts.CHUANGLAN_EMABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
