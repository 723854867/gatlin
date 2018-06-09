package org.gatlin.soa.sinapay;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaLoanout;
import org.gatlin.soa.sinapay.bean.param.WithdrawParam;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.RealnameParam;

public interface SinaBizHook {
	
	/**
	 * 实名认证
	 * 
	 * @param param
	 */
	UserSecurity realname(RealnameParam param);
	
	/**
	 * 绑卡
	 * 
	 * @param card
	 * @return
	 */
	void cardBind(BankCard card);
	
	/**
	 * 解绑银行卡
	 * 
	 * @param cardId
	 */
	void cardUnbind(String cardId);

	/**
	 * 标的报备审核
	 * 
	 * @param bid 标的
	 */
	void audit(SinaBid bid);
	
	/**
	 * 新浪放款回调
	 * 
	 * @param bid 标的
	 * @param loanout 放款记录
	 */
	void loanoutNotice(SinaBid bid, SinaLoanout loanout);
	
	/**
	 * 充值
	 * 
	 * @param recharge
	 */
	void recharge(Recharge recharge);
	
	/**
	 * 充值回调
	 * 
	 * @param id
	 * @param update
	 */
	void rechargeNotice(String id, RechargeState update);
	
	/**
	 * 提现
	 * 
	 * @return
	 */
	Withdraw withdraw(WithdrawParam param);
	
	/**
	 * 提现回调
	 * 
	 * @param id
	 * @param success
	 */
	void withdrawNotice(String id, boolean success);
}
