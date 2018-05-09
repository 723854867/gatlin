package org.gatlin.soa.account.bean;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.LogUserAccount;
import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

public class AccountUtil {
	
	public static final LogUserAccount log(long uid, BigDecimal amount, int bizType, String bizId) {
		return log(UserAccountType.BASIC, AccountField.USABLE, uid, amount, bizType, bizId);
	}

	public static final LogUserAccount log(UserAccountType type, long uid, BigDecimal amount, int bizType, String bizId) {
		return log(type, AccountField.USABLE, uid, amount, bizType, bizId);
	}
	
	public static final LogUserAccount log(UserAccountType type, AccountField field, long uid, BigDecimal amount, int bizType, String bizId) {
		LogUserAccount log = new LogUserAccount();
		log.setUid(uid);
		log.setBizId(bizId);
		log.setAmount(amount);
		log.setBizType(bizType);
		log.setFieldType(field.mark());
		log.setAccountType(type.mark());
		log.setCreated(DateUtil.current());
		log.setId(IDWorker.INSTANCE.nextSid());
		return log;
	}
}
