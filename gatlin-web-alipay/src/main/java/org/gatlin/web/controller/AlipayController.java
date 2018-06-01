package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.sdk.alipay.AlipayConfig;
import org.gatlin.sdk.alipay.bean.AlipayException;
import org.gatlin.sdk.alipay.bean.enums.Code;
import org.gatlin.sdk.alipay.bean.enums.TradeState;
import org.gatlin.sdk.alipay.notice.TradeNotice;
import org.gatlin.soa.SoaConsts;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.AccountUtil;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.alipay.api.AlipayAccountService;
import org.gatlin.soa.alipay.bean.param.RechargeParam;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.web.AlipayCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("alipay")
@Conditional(AlipayCondition.class)
public class AlipayController {
	
	@Resource
	private AccountService accountService;
	@Resource
	protected ConfigService configService;
	@Resource
	private AlipayAccountService alipayAccountService;
	
	// 支付宝账户充值
	@ResponseBody
	@RequestMapping("recharge/account")
	public Object recharge(@RequestBody @Valid RechargeParam param) {
		int timeout = configService.config(SoaConsts.RECHARGE_TIMEOUT);
		Recharge recharge = AccountUtil.newRecharge(param, PlatType.ALIPAY, 1, AccountType.BASIC, param.getAmount(), timeout);
		return alipayAccountService.recharge(recharge);
	}
	
	@ResponseBody
	@RequestMapping("notice/recharge")
	public Object noticeRecharge(@Valid TradeNotice param) {
		RechargeState state = _rechargeState(TradeState.valueOf(param.getTrade_status()));
		accountService.rechargeNotice(param.getOut_trade_no(), state);
		return AlipayConfig.SUCCESS;
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
