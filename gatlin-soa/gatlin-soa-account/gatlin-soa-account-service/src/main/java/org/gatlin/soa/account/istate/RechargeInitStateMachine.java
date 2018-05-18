package org.gatlin.soa.account.istate;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.bean.enums.BizType;

public abstract class RechargeInitStateMachine extends RechargeStateMachine {
	
	@Override
	public void process(Recharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:
		case TIMEOUT:
		case WAIT_PAY:			// 充值失败：处理响应充值失败的逻辑
			switch (recharge.getGoodsType()) {
			case 1:				// 个人账户充值失败
			case 2:				// 平台账户充值失败
			case 3:				// 企业账户充值失败
				break;
			default:
				rechargeFailure(recharge);
				break;
			}
			break;
		case FINISH:
		case SUCCESS:			// 充值成功：处理响应充值成功的逻辑
			switch (recharge.getGoodsType()) {
			case 1:				// 个人账户充值成功
				AccountType accountType = AccountType.match(Integer.valueOf(recharge.getGoodsId()));
				AccountDetail detail = AccountDetail.userUsable(recharge.getRechargee()).incr(recharge.getAmount())
						.bizId(recharge.getId()).bizType(BizType.RECHARGE_SUCCESS.mark()).accountType(accountType);
				accountManager.process(detail);
				break;
			case 2:				// 平台账户充值成功
				accountType = AccountType.match(Integer.valueOf(recharge.getGoodsId()));
				detail = AccountDetail.platUsable().incr(recharge.getAmount()).bizId(recharge.getId())
						.bizType(BizType.RECHARGE_SUCCESS.mark()).accountType(accountType);
				accountManager.process(detail);
				break;
			case 3:				// 企业账户充值成功
				accountType = AccountType.match(Integer.valueOf(recharge.getGoodsId()));
				detail = AccountDetail.companyUsable(recharge.getRechargee()).incr(recharge.getAmount())
						.bizId(recharge.getId()).bizType(BizType.RECHARGE_SUCCESS.mark()).accountType(accountType);
				accountManager.process(detail);
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
	
	protected abstract void rechargeFailure(Recharge recharge);
	
	protected abstract void rechargeSuccess(Recharge recharge);
}
