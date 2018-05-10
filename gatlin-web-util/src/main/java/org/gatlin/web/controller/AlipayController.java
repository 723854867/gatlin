package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.sdk.alipay.bean.AlipayException;
import org.gatlin.sdk.alipay.bean.enums.Code;
import org.gatlin.sdk.alipay.bean.enums.TradeState;
import org.gatlin.sdk.alipay.notice.TradeNotice;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("notice/alipay")
public class AlipayController {
	
	@Resource
	private AccountService accountService;

	@ResponseBody
	@RequestMapping("recharge")
	public Object recharge(@RequestBody @Valid TradeNotice param) {
		RechargeState state = _rechargeState(TradeState.valueOf(param.getTrade_status()));
		accountService.rechargeNotice(param.getOut_trade_no(), state);
		return "success";
	}
	
	private RechargeState _rechargeState(TradeState state) {
		switch (state) {
		case WAIT_BUYER_PAY:
			return RechargeState.WAIT_PAY;
		case TRADE_SUCCESS:
			return RechargeState.SUCCESS;
		case TRADE_FINISHED:
			return RechargeState.FINISH;
		case TRADE_CLOSED:
			return RechargeState.CLOSE;
		default:
			throw new AlipayException(Code.PARAM_ILLEGAL.name(), Code.PARAM_ILLEGAL.mark());
		}
	}
}
