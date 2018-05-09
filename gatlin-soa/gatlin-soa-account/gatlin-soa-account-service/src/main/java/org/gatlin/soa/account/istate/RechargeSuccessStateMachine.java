package org.gatlin.soa.account.istate;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;

public abstract class RechargeSuccessStateMachine extends RechargeStateMachine {

	@Override
	protected void process(UserRecharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:						// 用户退款
			rechargeReback(recharge);
			break;
		case FINISH:					// 充值订单关闭，用户不可退款什么都不做
			break;
		default:
			throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
		}
	}
	
	protected abstract void rechargeReback(UserRecharge recharge);
}
