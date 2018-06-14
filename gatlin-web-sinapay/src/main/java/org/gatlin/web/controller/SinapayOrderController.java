package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.SoaConsts;
import org.gatlin.soa.account.bean.AccountUtil;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.param.RechargeCompanyParam;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.sinapay.bean.param.WithdrawParam;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.api.CompanyService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.Employee;
import org.gatlin.web.Checker;
import org.gatlin.web.SinapayChecker;
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
	private Checker checker;
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
	
	// 托管充值(需要被充值人开了委托扣款，充值这已绑卡)：个人充值
	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object depositRecharge(@RequestBody @Valid RechargeParam param) {
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());			
		sinapayChecker.checkCardBind(MemberType.PERSONAL, param.getUser().getId());
		int timeout = configService.config(SoaConsts.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.SINAPAY, 1, AccountType.BASIC.mark(), param.getAmount(), timeout);
		return sinapayOrderService.depositRecharge(recharge, param);
	}
	
	// 托管充值(企业充值对私-使用雇员自己的账号充值)
	@ResponseBody
	@RequestMapping("recharge/deposit/company/c")
	public Object depositRechargeCompanyC(@RequestBody @Valid RechargeCompanyParam param) {
		Employee employee = checker.employeeVerify(param);
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());			
		sinapayChecker.checkCardBind(MemberType.PERSONAL, param.getUser().getId());
		int timeout = configService.config(SoaConsts.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.SINAPAY, 1, AccountType.DEPOSIT.mark(), param.getAmount(), timeout);
		recharge.setRechargeeType(TargetType.COMPANY);
		recharge.setRechargee(employee.getCompanyId());
		return sinapayOrderService.depositRecharge(recharge, param);
	}
	
	// 托管充值(企业充值对公-使用企业账户充值)
	@ResponseBody
	@RequestMapping("recharge/deposit/company/b")
	public Object depositRechargeCompanyB(@RequestBody @Valid RechargeCompanyParam param) {
		Employee employee = checker.employeeVerify(param);
		sinapayChecker.checkWithhold(MemberType.ENTERPRISE, employee.getCompanyId());
		sinapayChecker.checkCardBind(MemberType.ENTERPRISE, employee.getCompanyId());
		int timeout = configService.config(SoaConsts.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.SINAPAY, 1, AccountType.DEPOSIT.mark(), param.getAmount(), timeout);
		recharge.setRechargee(employee.getCompanyId());
		recharge.setRechargeeType(TargetType.COMPANY);
		recharge.setRecharger(employee.getCompanyId());
		recharge.setRechargerType(TargetType.COMPANY);
		return sinapayOrderService.depositRecharge(recharge, param);
	}
	
	// 个人托管提现代付
	@ResponseBody
	@RequestMapping("withdraw/deposit/pay")
	public Object depositWithdrawPay(@RequestBody @Valid WithdrawParam param) {
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());		
		sinapayChecker.checkCardBind(MemberType.PERSONAL, param.getUser().getId());
		return sinapayOrderService.withdrawPay(param);
	}
	
	// 提现
	@ResponseBody
	@RequestMapping("withdraw/deposit")
	public Object depositWithdraw(@RequestBody @Valid SoaSidParam param) {
		return sinapayOrderService.withdraw(param);
	}
}
