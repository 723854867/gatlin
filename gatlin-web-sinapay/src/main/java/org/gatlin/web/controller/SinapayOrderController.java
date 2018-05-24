package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.account.bean.AccountUtil;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.web.WebConsts;
import org.gatlin.web.util.hook.WithdrawHook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 新浪支付订单网关
 * 
 * @author lynn
 */
@Controller
@RequestMapping("sinapay/order")
public class SinapayOrderController {
	
	@Resource
	private UserService userService;
	@Resource
	private WithdrawHook withdrawHook;
	@Resource
	private ConfigService configService;
	@Resource
	private CompanyService companyService;
	@Resource
	private BankCardService bankCardService;
	@Resource
	private SinapayOrderService sinapayOrderService;
	@Resource
	private SinapayMemberService sinapayMemberService;

	// 托管充值
	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object depositRecharge(@RequestBody @Valid RechargeParam param) {
		int mod = 0;
		switch (param.getRechargeeType()) {
		case USER:						// 个人账户只有一种充值方式：个人->个人
			if (param.getRechargee() != param.getUser().getId()) 
				Assert.notNull(UserCode.USER_NOT_EIXST, userService.user(param.getRechargee()));
			mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_USER);
			break;
		case COMPANY:					// 对公账户有两种充值方式：个人->对公;对公->对公
			Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getRechargee().intValue()));
			mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_COMPANY);
			break;
		default:
			throw new CodeException(CoreCode.PARAM_ERR);
		}
		AccountType accountType = param.getAccountType();
		Assert.isTrue(CoreCode.PARAM_ERR, (accountType.mark() & mod) == accountType.mark());
		if (null != param.getCompanyId()) {			// 对公账户充值
			Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getCompanyId()));
			Assert.isTrue(SinaCode.COMPANY_UNWITHHOLD, sinapayMemberService.isWithhold(MemberType.ENTERPRISE, String.valueOf(param.getCompanyId())));
			BankCardsParam bp = new BankCardsParam();
			bp.setOwner(Long.valueOf(param.getCompanyId()));
			bp.setOwnerType(TargetType.COMPANY.mark());
			Pager<BankCardInfo> pager = bankCardService.cards(bp);
			Assert.isTrue(SinaCode.BANK_CARD_BIND_NOT_EXIST, !CollectionUtil.isEmpty(pager.getList()));
		} else {
			Assert.isTrue(SinaCode.USER_UNWITHHOLD, sinapayMemberService.isWithhold(MemberType.PERSONAL, String.valueOf(param.getUser().getId())));
			BankCardsParam bp = new BankCardsParam();
			bp.setOwner(param.getUser().getId());
			bp.setOwnerType(TargetType.USER.mark());
			Pager<BankCardInfo> pager = bankCardService.cards(bp);
			Assert.isTrue(SinaCode.BANK_CARD_BIND_NOT_EXIST, !CollectionUtil.isEmpty(pager.getList()));
		}
		int timeout = configService.config(WebConsts.Options.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.SINAPAY, 1, accountType.mark(), param.getAmount(), timeout);
		recharge.setRechargee(param.getRechargee());
		recharge.setRechargeeType(param.getRechargeeType().mark());
		if (null != param.getCompanyId()) {
			recharge.setRecharger(param.getCompanyId());
			recharge.setRechargerType(TargetType.COMPANY.mark());
		}
		return sinapayOrderService.depositRecharge(recharge, param);
	}
	
	// 托管提现代付
	@ResponseBody
	@RequestMapping("withdraw/deposit/pay")
	public Object depositWithdrawPay(@RequestBody @Valid WithdrawParam param) {
		param.setPlat(PlatType.SINAPAY);
		withdrawHook.verify(param);
		return sinapayOrderService.withdrawPay(param);
	}
	
	// 提现
	@ResponseBody
	@RequestMapping("withdraw/deposit")
	public Object depositWithdraw(@RequestBody @Valid SoaSidParam param) {
		return sinapayOrderService.withdraw(param);
	}
}
