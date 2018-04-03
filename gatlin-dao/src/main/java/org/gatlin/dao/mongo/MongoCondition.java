package org.gatlin.dao.mongo;

import org.gatlin.core.condition.GatlinCondition;
import org.gatlin.dao.DaoConsts;

/**
 * 只要有一个模块开启 mongo就会启用mongo
 * 
 * @author lynn
 */
public class MongoCondition extends GatlinCondition<Boolean> {

	public MongoCondition() {
		super(DaoConsts.Options.MONGO_ENABLE);
	}
	
	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
