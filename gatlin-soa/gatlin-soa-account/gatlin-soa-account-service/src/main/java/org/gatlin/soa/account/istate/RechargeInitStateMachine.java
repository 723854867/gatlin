package org.gatlin.soa.account.istate;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.AccountUtil;
import org.gatlin.soa.account.bean.entity.LogUserAccount;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.bean.enums.BizType;

public abstract class RechargeInitStateMachine extends RechargeStateMachine {
	
	@Override
	public void process(UserRecharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:
		case TIMEOUT:
		case WAIT_PAY:			// 充值失败：处理响应充值失败的逻辑
			switch (recharge.getGoodsType()) {
			case 1:				// 充值货币失败，什么都不处理
				break;
			default:
				rechargeFailure(recharge);
				break;
			}
			break;
		case FINISH:
		case SUCCESS:			// 充值成功：处理响应充值成功的逻辑
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
