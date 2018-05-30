package org.gatlin.soa.account.hook;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.RechargeState;

public interface IRechargeNoticeHook {

	void process(Recharge recharge, RechargeState update);
}
