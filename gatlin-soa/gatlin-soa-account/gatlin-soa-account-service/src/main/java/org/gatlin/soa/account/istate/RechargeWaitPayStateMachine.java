package org.gatlin.soa.account.istate;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.AccountUtil;
import org.gatlin.soa.account.bean.entity.LogUserAccount;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.bean.enums.BizType;

public abstract class RechargeWaitPayStateMachine extends RechargeStateMachine {
	
	@Override
	protected void process(UserRecharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:
			switch (recharge.getGoodsType()) {
			case 1:				// 充值货币失败，什么都不处理
				break;
			default:
				rechargeFailure(recharge);
				break;
			}
			break;
		case FINISH:
		case SUCCESS:
			switch (recharge.getGoodsType()) {
			case 1:				// 充值货币成功，什么都不处理
				UserAccountType accountType = UserAccountType.match(Integer.valueOf(recharge.getGoodsId()));
				LogUserAccount log = AccountUtil.log(accountType, recharge.getRechargee(), recharge.getAmount().subtract(recharge.getFee()), BizType.RECHARGE_SUCCESS.mark(), recharge.getId());
				accountManager.process(log);
				break;
			default:
				rechargeSuccess(recharge);
				break;
			}
			break;
		default:
			throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
		}
	}
	
	protected abstract void rechargeFailure(UserRecharge recharge);
	
	protected abstract void rechargeSuccess(UserRecharge recharge);
}
