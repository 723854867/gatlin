package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.PlatType;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.param.SinaRechargeParam;
import org.gatlin.web.SinapayCondition;
import org.gatlin.web.util.hook.RechargeHook;
import org.springframework.context.annotation.Conditional;
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
@Conditional(SinapayCondition.class)
public class SinapayOrderController {
	
	@Resource
	private SinapayOrderService sinapayOrderService;
	@Resource
	private RechargeHook<SinaRechargeParam> rechargeHook;

	// 托管充值
	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object depositRecharge(@RequestBody @Valid SinaRechargeParam param) {
		Recharge recharge = rechargeHook.rechargeVerify(param, PlatType.ALIPAY);
		return sinapayOrderService.depositRecharge(recharge, param);
	}
}
