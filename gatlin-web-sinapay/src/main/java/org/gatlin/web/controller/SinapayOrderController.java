package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.param.RechargeParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.web.util.hook.RechargeHook;
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
	private RechargeHook rechargeHook;
	@Resource
	private WithdrawHook withdrawHook;
	@Resource
	private SinapayOrderService sinapayOrderService;

	// 托管充值
	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object depositRecharge(@RequestBody @Valid RechargeParam param) {
		Recharge recharge = rechargeHook.rechargeVerify(param, PlatType.ALIPAY);
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
