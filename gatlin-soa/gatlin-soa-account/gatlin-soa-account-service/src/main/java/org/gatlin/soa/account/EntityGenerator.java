package org.gatlin.soa.account;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.DateUtil;

public class EntityGenerator {

	public static final Account newUserAccount(long uid, int type) {
		Account instance = new Account();
		instance.setType(type);
		instance.setOwner(uid);
		instance.setOwnerType(TargetType.USER.mark());
		instance.setUsable(BigDecimal.ZERO);
		instance.setFrozen(BigDecimal.ZERO);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
