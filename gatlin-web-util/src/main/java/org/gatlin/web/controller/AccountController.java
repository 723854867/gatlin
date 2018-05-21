package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.param.AccountListParam;
import org.gatlin.soa.account.bean.param.RechargesParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Resource
	private AccountService accountService;
	
	@ResponseBody
	@RequestMapping("user/list")
	public Object list(@RequestBody @Valid AccountListParam param) {
		return accountService.accounts(param.query());
	}
	
	@ResponseBody
	@RequestMapping("recharges")
	public Object recharges(@RequestBody @Valid RechargesParam param) {
		return accountService.recharges(param.query());
	}
	
	@ResponseBody
	@RequestMapping("user/recharges")
	public Object userRecharges(@RequestBody @Valid RechargesParam param) {
		Query query = param.query();
		query.eq("recharger", param.getUser().getId());
		return accountService.recharges(query);
	}
}
