package org.gatlin.soa.account.hook;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.account.manager.AccountManager;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.GatlinBizType;
import org.gatlin.soa.bean.enums.TargetType;
import org.springframework.stereotype.Component;

@Component
public class DefaultRechargeNoticeHook implements IRechargeNoticeHook {
	
	@Resource
	private AccountManager accountManager;

	@Override
	public void process(Recharge recharge, RechargeState update) {
		RechargeState pstate = RechargeState.match(recharge.getState());
		switch (pstate) {
		case INIT:
			initUpdate(recharge, update);
			break;
		case SUCCESS:
			successUpdate(recharge, update);
			break;
		case WAIT_PAY:
			waitPayUpdate(recharge, update);
			break;
		default:
			throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
		}
	}
	
	protected void initUpdate(Recharge recharge, RechargeState state) { 
		switch (state) {
		case CLOSE:
		case TIMEOUT:
		case WAIT_PAY:			// 充值失败：处理响应充值失败的逻辑
			failure(recharge);
			break;
		case FINISH:
		case SUCCESS:			// 充值成功：处理响应充值成功的逻辑
			success(recharge);
			break;
		default:
			throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
		}
	}
	
	protected void successUpdate(Recharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:						// 用户退款
			refund(recharge);
			break;
		case FINISH:					// 充值订单关闭，用户不可退款什么都不做
			break;
		default:
			throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
		}
	}
	
	protected void waitPayUpdate(Recharge recharge, RechargeState state) {
		switch (state) {
		case CLOSE:
			failure(recharge);
			break;
		case FINISH:
		case SUCCESS:
			success(recharge);
			break;
		default:
			throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
		}
	}
	
	// 充值失败
	protected void failure(Recharge recharge) {
		switch (recharge.getGoodsType()) {
		case 1:				// 账户充值失败
			break;
		default:
			break;
		}
	}
	
	// 充值退款
	protected void refund(Recharge recharge) {
		throw new CodeException(AccountCode.RECHARGE_STATE_ERR);
	}
	
	// 充值成功
	protected void success(Recharge recharge) { 
		switch (recharge.getGoodsType()) {
		case 1:				// 账户充值成功
			TargetType rechargeeType = TargetType.match(recharge.getRechargeeType());
			AccountType accountType = AccountType.match(Integer.valueOf(recharge.getGoodsId()));
			AccountDetail detail = new AccountDetail(recharge.getId(), GatlinBizType.RECHARGE_SUCCESS);
			BigDecimal amount = recharge.getAmount().subtract(recharge.getFee());
			detail.usableIncr(rechargeeType, recharge.getRechargee(), accountType, amount);
			accountManager.process(detail);
			break;
		default:
			break;
		}
	}
}
