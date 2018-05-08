package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.entity.UserRecharge;
import org.gatlin.soa.account.bean.param.AccountListParam;
import org.gatlin.soa.account.bean.param.RechargeParam;
import org.gatlin.web.util.hook.RechargeHook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Resource
	private RechargeHook rechargeHook;
	@Resource
	private AccountService accountService;
	
	@ResponseBody
	@RequestMapping("user/list")
	public Object list(@RequestBody @Valid AccountListParam param) {
		return accountService.accounts(param.query());
	}
	
	@ResponseBody
	@RequestMapping("recharge")
	public Object recharge(@RequestBody @Valid RechargeParam param) {
		UserRecharge recharge = rechargeHook.rechargeVerify(param);
		return accountService.recharge(recharge);
	}
}
