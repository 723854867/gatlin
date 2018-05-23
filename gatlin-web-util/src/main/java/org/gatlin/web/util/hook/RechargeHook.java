package org.gatlin.web.util.hook;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.RechargeParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.web.WebConsts;

public abstract class RechargeHook {
	
	@Resource
	protected UserService userService;
	@Resource
	protected ConfigService configService;
	@Resource
	protected CompanyService companyService;

	public Recharge rechargeVerify(RechargeParam param, PlatType plat) {
		try {
			int mod = 0;
			switch (param.getRechargeeType()) {
			case USER:
				if (param.getRechargee() != param.getUser().getId()) 
					Assert.notNull(UserCode.USER_NOT_EIXST, userService.user(param.getRechargee()));
				if (null != param.getCompanyId())			// 对公账户充值到个人账户
					Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getCompanyId()));
				mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_USER);
				break;
			case COMPANY:				// 对公账户充值
				Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getRechargee().intValue()));
				if (null != param.getCompanyId() && param.getCompanyId() != param.getRechargee().intValue())	// 对公账户充值转账到另外的对公账户
					Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getCompanyId()));
				mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_COMPANY);
				break;
			case PLATFORM:
				mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_PLAT);
				break;
			default:
				throw new CodeException(CoreCode.PARAM_ERR);
			}
			check(param);
			switch (param.getGoodsType()) {
			case 1:
				Assert.isTrue(CoreCode.PARAM_ERR, null != param.getAmount() && param.getAmount().compareTo(BigDecimal.ZERO) > 0);
				param.setAmount(param.getAmount().setScale(2, RoundingMode.UP));
				AccountType accountType = AccountType.match(Integer.valueOf(param.getGoodsId()));
				Assert.notNull(CoreCode.PARAM_ERR, accountType);
				Assert.isTrue(CoreCode.PARAM_ERR, (accountType.mark() & mod) == accountType.mark());
				return _recharge(param, plat);
			default:
				return verify(param, plat);
			}
		} catch (Exception e) {
			if (e instanceof CodeException)
				throw e;
			throw new CodeException(CoreCode.PARAM_ERR, e);
		}
	}
	
	protected void check(RechargeParam param) {};
	
	protected abstract Recharge verify(RechargeParam param, PlatType plat);
	
	protected Recharge _recharge(RechargeParam param, PlatType plat) {
		Recharge instance = new Recharge();
		instance.setId(IDWorker.INSTANCE.nextSid());
		User user = param.getUser();
		instance.setOs(user.getOs().mark());
		instance.setPlat(plat.mark());
		instance.setIp(param.meta().getIp());
		instance.setState(RechargeState.INIT.mark());
		instance.setGoodsType(param.getGoodsType());
		instance.setGoodsId(param.getGoodsId());
		instance.setRechargee(param.getRechargee());
		instance.setRechargeeType(param.getRechargeeType().mark());
		if (null == param.getCompanyId()) {
			instance.setRecharger(param.getUser().getId());
			instance.setRechargerType(TargetType.USER.mark());
		} else  {
			instance.setRecharger(param.getCompanyId());
			instance.setRechargerType(TargetType.COMPANY.mark());
		}
		instance.setOperator(param.getUser().getId());
		instance.setFee(param.getFee());
		instance.setAmount(param.getAmount());
		int timeout = configService.config(WebConsts.Options.RECHARGE_TIMEOUT);
		timeout = Math.max(0, timeout);
		int time = DateUtil.current();
		instance.setExpiry(time + timeout * 60);
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
