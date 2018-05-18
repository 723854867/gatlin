package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.util.serial.SerializeUtil;
import org.gatlin.web.SinapayCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 新浪支付通知
 * 
 * @author lynn
 */
@Controller
@RequestMapping("sinapay/notice")
@Conditional(SinapayCondition.class)
public class SinapayNoticeController {
	
	@Resource
	private SinapayOrderService sinapayOrderService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setFieldMarkerPrefix("⊙");
	}

	@ResponseBody
	@RequestMapping("recharge/deposit")
	public Object rechargeDeposit(@Valid DepositRechargeNotice notice) {
		System.out.println(SerializeUtil.GSON.toJson(notice));
		return null;
	}
}
