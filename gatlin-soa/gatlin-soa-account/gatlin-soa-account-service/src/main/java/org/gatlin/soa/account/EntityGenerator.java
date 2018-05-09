package org.gatlin.soa.account;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.LogUserAccount;
import org.gatlin.soa.account.bean.entity.UserAccount;
import org.gatlin.soa.account.bean.entity.UserFrozen;
import org.gatlin.soa.account.bean.enums.FrozenState;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

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
	
	public static final UserFrozen newUserFrozen(LogUserAccount log) {
		UserFrozen instance = new UserFrozen();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setBizId(log.getBizId());
		instance.setBizType(log.getBizType());
		instance.setAmount(log.getAmount().negate());
		instance.setState(FrozenState.FREEZING.mark());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
