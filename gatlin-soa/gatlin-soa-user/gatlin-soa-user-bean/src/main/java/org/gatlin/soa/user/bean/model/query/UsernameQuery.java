package org.gatlin.soa.user.bean.model.query;

import org.gatlin.dao.bean.enums.Comparison;
import org.gatlin.dao.bean.model.Condition;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.user.bean.enums.UsernameType;

public class UsernameQuery extends Query<UsernameQuery> {

	private static final long serialVersionUID = 7716823627159520503L;

	public UsernameQuery type(UsernameType type) {
		return addCondition(new Condition("type", Comparison.eq, type.mark()));
	}
	
	public UsernameQuery username(String username) {
		return addCondition(new Condition("username", Comparison.eq, username));
	}
}
