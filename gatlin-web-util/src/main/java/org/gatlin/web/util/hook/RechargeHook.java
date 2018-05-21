package org.gatlin.web.util.hook;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.PlatType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.param.RechargeParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.web.WebConsts;

public abstract class RechargeHook<PARAM extends RechargeParam> {
	
	@Resource
	private UserService userService;
	@Resource
	private CompanyService companyService;
	@Resource
	protected ConfigService configService;

	public Recharge rechargeVerify(PARAM param, PlatType plat) {
		try {
			switch (param.getGoodsType()) {
			case 1:
				userRechargeeVerify(param);
				Assert.isTrue(CoreCode.PARAM_ERR, null != param.getAmount() && param.getAmount().compareTo(BigDecimal.ZERO) > 0);
				param.setAmount(param.getAmount().setScale(2, RoundingMode.UP));
				AccountType accountType = AccountType.match(Integer.valueOf(param.getGoodsId()));
				Assert.notNull(CoreCode.PARAM_ERR, accountType);
				int mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_USER);
				Assert.isTrue(CoreCode.PARAM_ERR, (accountType.mark() & mod) == accountType.mark());
				return _recharge(param, plat);
			case 2:
				param.setRechargee(0l);
				Assert.isTrue(CoreCode.PARAM_ERR, null != param.getAmount() && param.getAmount().compareTo(BigDecimal.ZERO) > 0);
				param.setAmount(param.getAmount().setScale(2, RoundingMode.UP));
				accountType = AccountType.match(Integer.valueOf(param.getGoodsId()));
				Assert.notNull(CoreCode.PARAM_ERR, accountType);
				mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_PLAT);
				Assert.isTrue(CoreCode.PARAM_ERR, (accountType.mark() & mod) == accountType.mark());
				return _recharge(param, plat);
			case 3:
				companyRechargeeVerify(param);
				Assert.isTrue(CoreCode.PARAM_ERR, null != param.getAmount() && param.getAmount().compareTo(BigDecimal.ZERO) > 0);
				param.setAmount(param.getAmount().setScale(2, RoundingMode.UP));
				accountType = AccountType.match(Integer.valueOf(param.getGoodsId()));
				Assert.notNull(CoreCode.PARAM_ERR, accountType);
				mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_COMPANY);
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
	
	protected void userRechargeeVerify(RechargeParam param) {
		if (null == param.getRechargee()) 
			param.setRechargee(param.getUser().getId());
		else {
			if (param.getRechargee() != param.getUser().getId())
				Assert.notNull(UserCode.USER_NOT_EIXST, userService.user(param.getRechargee()));
		}
	}
	
	protected void companyRechargeeVerify(RechargeParam param) {
		Assert.notNull(CoreCode.PARAM_ERR, param.getRechargee());
		Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getRechargee().intValue()));
	}
	
	protected abstract Recharge verify(RechargeParam param, PlatType plat);
	
	protected Recharge _recharge(RechargeParam param, PlatType plat) {
		return _recharge(param, plat, BigDecimal.ZERO);
	}
	
	protected Recharge _recharge(RechargeParam param, PlatType plat, BigDecimal fee) {
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
		instance.setRecharger(user.getId());
		instance.setFee(fee);
		instance.setAmount(param.getAmount().add(fee));
		int timeout = configService.config(WebConsts.Options.RECHARGE_TIMEOUT);
		timeout = Math.max(0, timeout);
		int time = DateUtil.current();
		instance.setExpiry(time + timeout * 60);
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
