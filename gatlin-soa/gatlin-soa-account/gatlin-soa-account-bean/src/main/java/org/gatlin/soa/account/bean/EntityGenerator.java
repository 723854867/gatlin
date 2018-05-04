package org.gatlin.soa.account.bean;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.util.DateUtil;

public class EntityGenerator {

	public static final UserAccount newUserAccount(long uid, int type) {
		UserAccount instance = new UserAccount();
		instance.setUid(uid);
		instance.setType(type);
		instance.setUsable(BigDecimal.ZERO);
		instance.setFrozen(BigDecimal.ZERO);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
