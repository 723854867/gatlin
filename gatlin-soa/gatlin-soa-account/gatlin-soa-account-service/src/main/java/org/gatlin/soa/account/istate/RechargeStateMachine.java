package org.gatlin.soa.account.istate;

import javax.annotation.Resource;

import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.manager.AccountManager;
import org.gatlin.util.DateUtil;

public abstract class RechargeStateMachine {
	
	@Resource
	protected AccountManager accountManager;

	public void update(UserRecharge recharge, RechargeState state) {
		recharge.setUpdated(state.mark());
		recharge.setUpdated(DateUtil.current());
		process(recharge, state);
	}
	
	protected abstract void process(UserRecharge recharge, RechargeState state);
}
