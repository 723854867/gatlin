package org.gatlin.web.util.validator;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.bean.model.WithdrawContext;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.web.WebConsts;
import org.springframework.stereotype.Component;

// 普通提现校验
@Component
public class WithdrawValidator implements IWithdrawValidator {
	
	@Override
	public void validate(WithdrawContext context) {
		WithdrawParam param = context.getParam();
		int mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_WITHDRAW_MOD_USER);
		Assert.isTrue(AccountCode.ACCOUNT_UNSUPPORT_WITHDRAW, (param.getAccountType().mark() & mod) == param.getAccountType().mark());
	}
}
