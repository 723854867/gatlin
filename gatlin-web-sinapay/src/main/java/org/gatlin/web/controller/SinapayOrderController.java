package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.util.Assert;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.SoaConsts;
import org.gatlin.soa.account.bean.AccountUtil;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.model.WithdrawContext;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.param.RechargeCompanyParam;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.web.SinapayChecker;
import org.gatlin.web.WebConsts;
import org.gatlin.web.util.validator.Validators;
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
	private Validators validators;
	@Resource
	private UserService userService;
	@Resource
	private ConfigService configService;
	@Resource
	private SinapayChecker sinapayChecker;
	@Resource
	private CompanyService companyService;
	@Resource
	private BankCardService bankCardService;
	@Resource
	private SinapayOrderService sinapayOrderService;
	@Resource
	private SinapayMemberService sinapayMemberService;
	
	// 托管充值(需要被充值人开了委托扣款，充值这已绑卡)：可以实现个人充值企业，个人充值个人
	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object depositRecharge(@RequestBody @Valid RechargeParam param) {
		int mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_USER);
		Assert.isTrue(CoreCode.PARAM_ERR, (param.getAccountType().mark() & mod) == param.getAccountType().mark());
		MemberType rechargeeType = param.getRechargeeType() == TargetType.COMPANY ? MemberType.ENTERPRISE : MemberType.PERSONAL;
		sinapayChecker.checkWithhold(rechargeeType, param.getRechargee());			
		sinapayChecker.checkCardBind(MemberType.PERSONAL, param.getUser().getId());
		int timeout = configService.config(SoaConsts.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.SINAPAY, 1, param.getAccountType().mark(), param.getAmount(), timeout);
		recharge.setRechargee(param.getRechargee());
		recharge.setRechargeeType(param.getRechargeeType().mark());
		return sinapayOrderService.depositRecharge(recharge, param);
	}
	
	// 托管充值(需要被充值人开了委托扣款，充值这已绑卡)：只可以企业充值企业
	@ResponseBody
	@RequestMapping("recharge/deposit/company")
	public Object depositRechargeCompany(@RequestBody @Valid RechargeCompanyParam param) {
		int mod = GatlinConfigration.get(WebConsts.Options.ACCOUNT_RECHARGE_MOD_COMPANY);
		Assert.notNull(UserCode.COMPANY_NOT_EIXST, companyService.company(param.getRechargee().intValue()));
		Assert.isTrue(CoreCode.PARAM_ERR, (param.getAccountType().mark() & mod) == param.getAccountType().mark());
		sinapayChecker.checkWithhold(MemberType.ENTERPRISE, param.getCompanyId());
		sinapayChecker.checkCardBind(MemberType.ENTERPRISE, param.getCompanyId());
		int timeout = configService.config(SoaConsts.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.SINAPAY, 1, param.getAccountType().mark(), param.getAmount(), timeout);
		recharge.setRechargee(param.getCompanyId());
		recharge.setRechargeeType(TargetType.COMPANY.mark());
		recharge.setRecharger(param.getCompanyId());
		recharge.setRechargerType(TargetType.COMPANY.mark());
		return sinapayOrderService.depositRecharge(recharge, param);
	}
	
	// 个人托管提现代付
	@ResponseBody
	@RequestMapping("withdraw/deposit/pay/user")
	public Object depositWithdrawPay(@RequestBody @Valid WithdrawParam param) {
		WithdrawContext context = new WithdrawContext(PlatType.SINAPAY, param);
		validators.withdraw(context);
		return sinapayOrderService.withdrawPay(param, context);
	}
	
	// 提现
	@ResponseBody
	@RequestMapping("withdraw/deposit")
	public Object depositWithdraw(@RequestBody @Valid SoaSidParam param) {
		return sinapayOrderService.withdraw(param);
	}
}
