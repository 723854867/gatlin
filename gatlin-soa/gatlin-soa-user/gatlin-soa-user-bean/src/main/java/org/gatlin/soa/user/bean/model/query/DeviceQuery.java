package org.gatlin.soa.user.bean.model.query;

import org.gatlin.dao.bean.enums.Comparison;
import org.gatlin.dao.bean.model.Condition;
import org.gatlin.dao.bean.model.Query;

public class DeviceQuery extends Query<DeviceQuery> {

	private static final long serialVersionUID = -8232928411068504197L;

	public DeviceQuery uid(long uid) {
		return addCondition(new Condition("uid", Comparison.eq, uid));
	}
	
	public DeviceQuery type(int type) {
		return addCondition(new Condition("type", Comparison.eq, type));
	}
	
	public DeviceQuery token(String token) {
		return addCondition(new Condition("token", Comparison.eq, token));
	}
}
