package org.gatlin.soa.account.istate;

import java.math.BigDecimal;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.BizType;
import org.gatlin.soa.bean.enums.TargetType;

public abstract class RechargeWaitPayStateMachine extends RechargeStateMachine {
	
	@Override
	protected void process(Recharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:
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
		case SUCCESS:
			switch (recharge.getGoodsType()) {
			case 1:				// 个人账户充值成功
				TargetType rechargeeType = TargetType.match(recharge.getRechargeeType());
				AccountType accountType = AccountType.match(Integer.valueOf(recharge.getGoodsId()));
				AccountDetail detail = new AccountDetail(recharge.getId(), BizType.RECHARGE_SUCCESS);
				BigDecimal amount = recharge.getAmount().subtract(recharge.getFee());
				detail.usableIncr(rechargeeType, recharge.getRechargee(), accountType, amount);
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
