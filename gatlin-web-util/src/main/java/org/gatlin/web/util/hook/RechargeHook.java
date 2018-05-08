package org.gatlin.web.util.hook;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.account.bean.enums.UserAccountType;
import org.gatlin.soa.account.bean.param.RechargeParam;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.web.util.WebConsts;

public abstract class RechargeHook {
	
	@Resource
	protected ConfigService configService;

	public UserRecharge rechargeVerify(RechargeParam param) {
		try {
			switch (param.getGoodsType()) {
			case 1:
				UserAccountType accountType = UserAccountType.match(Integer.valueOf(param.getGoodsId()));
				Assert.notNull(CoreCode.PARAM_ERR, accountType);
				int mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD);
				Assert.isTrue(CoreCode.PARAM_ERR, (accountType.mark() & mod) != accountType.mark());
				return _userRecharge(param);
			default:
				return verify(param);
			}
		} catch (Exception e) {
			if (e instanceof CodeException)
				throw e;
			throw new CodeException(CoreCode.PARAM_ERR, e);
		}
	}
	
	protected abstract UserRecharge verify(RechargeParam param);
	
	protected UserRecharge _userRecharge(RechargeParam param) {
		return _userRecharge(param, BigDecimal.ZERO);
	}
	
	protected UserRecharge _userRecharge(RechargeParam param, BigDecimal fee) {
		UserRecharge instance = new UserRecharge();
		instance.setId(IDWorker.INSTANCE.nextSid());
		User user = param.getUser();
		instance.setOs(user.getOs().mark());
		instance.setPlat(param.getPlat().mark());
		instance.setIp(param.meta().getIp());
		instance.setState(RechargeState.INIT.mark());
		instance.setGoodsType(param.getGoodsType());
		instance.setGoodsId(param.getGoodsId());
		instance.setRechargee(param.getRechargee());
		instance.setRecharger(user.getId());
		instance.setFee(fee);
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
