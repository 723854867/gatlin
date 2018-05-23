package org.gatlin.soa.account;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.enums.WithdrawState;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

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
	
	public static final Withdraw newWithdraw(WithdrawParam param) {
		User user = param.getUser();
		Withdraw instance = new Withdraw();
		instance.setFee(param.getFee());
		instance.setOperator(user.getId());
		instance.setOs(user.getOs().mark());
		instance.setIp(param.meta().getIp());
		instance.setAmount(param.getAmount());
		instance.setPlat(param.getPlat().mark());
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setState(WithdrawState.INIT.mark());
		instance.setWithdrawee(param.getWithdrawee());
		instance.setWithdrawerAccountType(param.getAccountType().mark());
		instance.setWithdraweeType(param.getWithdraweeType().mark());
		if (null == param.getCompanyId()) {
			instance.setWithdrawer(param.getWithdrawee());
			instance.setWithdrawerType(TargetType.USER.mark());
		} else {
			instance.setWithdrawer(param.getCompanyId());
			instance.setWithdrawerType(TargetType.COMPANY.mark());
		}
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
