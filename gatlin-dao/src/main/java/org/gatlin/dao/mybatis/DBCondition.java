package org.gatlin.dao.mybatis;

import org.gatlin.core.condition.GatlinCondition;
import org.gatlin.dao.DaoConsts;

public class DBCondition extends GatlinCondition<Boolean> {

	public DBCondition() {
		super(DaoConsts.Options.DB_ENABLE);
	}

	@Override
	protected boolean checkCondition(Boolean value) {
		return value;
	}
}
