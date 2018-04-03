package org.gatlin.soa.config.bean.model.query;

import org.gatlin.dao.bean.enums.Comparison;
import org.gatlin.dao.bean.model.Condition;
import org.gatlin.dao.bean.model.Query;

public class ConfigQuery extends Query<ConfigQuery> {

	private static final long serialVersionUID = -353152988681927798L;

	public ConfigQuery type(int type) {
		return addCondition(new Condition("type", Comparison.eq, type));
	}
}
