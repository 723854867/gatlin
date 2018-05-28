package org.gatlin.web.validator;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.alipay.bean.model.RechargeContext;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.web.WebConsts;
import org.springframework.stereotype.Component;

@Component
public class AlipayRechargeValidator implements IAlipayRechargeValidator {

	@Override
	public void validate(RechargeContext context) {
		int mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_USER);
		AccountType accountType = context.getParam().getAccountType();
		Assert.isTrue(CoreCode.PARAM_ERR, (accountType.mark() & mod) == accountType.mark());
	}
}
