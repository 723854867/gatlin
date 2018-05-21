package org.gatlin.soa.account.istate;

import javax.annotation.Resource;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.manager.AccountManager;
import org.gatlin.util.DateUtil;

public abstract class RechargeStateMachine {
	
	@Resource
	protected AccountManager accountManager;

	public void update(Recharge recharge, RechargeState state) {
		recharge.setState(state.mark());
		recharge.setUpdated(DateUtil.current());
		process(recharge, state);
	}
	
	protected abstract void process(Recharge recharge, RechargeState state);
}
