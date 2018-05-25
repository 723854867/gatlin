package org.gatlin.soa.account;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.Account;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.enums.WithdrawState;
import org.gatlin.soa.bean.model.WithdrawContext;
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
	
	public static final Withdraw newWithdraw(WithdrawParam param, WithdrawContext context) {
		User user = param.getUser();
		Withdraw instance = new Withdraw();
		instance.setFee(context.getFee());
		instance.setOperator(user.getId());
		instance.setOs(user.getOs().mark());
		instance.setIp(param.meta().getIp());
		instance.setAmount(param.getAmount());
		instance.setPlat(context.getPlat().mark());
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setState(WithdrawState.INIT.mark());
		instance.setWithdrawee(param.getUser().getId());
		instance.setWithdrawerAccountType(param.getAccountType().mark());
		instance.setWithdraweeType(TargetType.USER.mark());
		instance.setWithdrawer(instance.getWithdrawee());
		instance.setWithdrawerType(TargetType.USER.mark());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
