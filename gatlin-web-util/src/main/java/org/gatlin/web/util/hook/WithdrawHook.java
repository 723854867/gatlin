package org.gatlin.web.util.hook;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.Gatlin;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.account.bean.AccountCode;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.web.WebConsts;
import org.springframework.stereotype.Component;

@Component
public class WithdrawHook {
	
	@Resource
	protected Gatlin gatlin;
	@Resource
	protected UserService userService;
	@Resource
	protected CompanyService companyService;

	public void verify(WithdrawParam param) {
		int mod = 0;
		switch (param.getWithdraweeType()) {
		case USER:
			if (null != param.getCompanyId()) 			// 企业账户提现到个人账户：需要严格的校验
				companyService.withdraw2userVerify(param.getCompanyId(), param.getWithdrawee());
			mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_WITHDRAW_MOD_USER);
			break;
		case COMPANY:
			Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getWithdrawee().intValue()));
			mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_WITHDRAW_MOD_COMPANY);
			break;
		default:
			throw new CodeException(CoreCode.FORBID);
		}
		Assert.isTrue(AccountCode.ACCOUNT_UNSUPPORT_WITHDRAW, (param.getAccountType().mark() & mod) == param.getAccountType().mark());
	}
}
