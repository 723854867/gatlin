package org.gatlin.soa.alipay.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.sdk.alipay.SignUtil;
import org.gatlin.sdk.alipay.request.AppPayRequest;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.util.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class AlipayAccountManager {
	
	@Resource
	private AccountService accountService;

	public String recharge(UserRecharge recharge) {
		accountService.recharge(recharge);
		AppPayRequest.Builder builder = new AppPayRequest.Builder();
		builder.subject("充值");
		builder.outerTradeNo(recharge.getId());
		builder.totalAmount(recharge.getAmount());
		int minutes = (recharge.getExpiry() - DateUtil.current()) / 60;
		minutes -= 5;			// 第三方的充值过期时间比自身的过期时间要少5分钟，确保自身回调过期时第三方的充值也已经过期
		Assert.isTrue(AccountCode.RECHARGE_EXPIRY_TIME_ERR, minutes >= 1);
		builder.timeoutExpress(minutes + "m");
		AppPayRequest pay = builder.build();
		Map<String, String> params = pay.params();
		return SignUtil.buildQuery(params);
	}
}
