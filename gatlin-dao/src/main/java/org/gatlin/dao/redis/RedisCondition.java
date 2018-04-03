package org.gatlin.dao.redis;

import org.gatlin.core.condition.GatlinCondition;
import org.gatlin.dao.DaoConsts;

public class RedisCondition extends GatlinCondition<Boolean> {

	public RedisCondition() {
		super(DaoConsts.Options.REDIS_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
