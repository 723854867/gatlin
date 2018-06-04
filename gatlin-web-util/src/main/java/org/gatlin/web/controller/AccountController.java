package org.gatlin.web.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.model.AccountDetail;
import org.gatlin.soa.account.bean.param.AccountListParam;
import org.gatlin.soa.account.bean.param.AdjustParam;
import org.gatlin.soa.account.bean.param.RechargesParam;
import org.gatlin.soa.bean.enums.GatlinBizType;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.NumberUtil;
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
	@RequestMapping("list")
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
	
	@ResponseBody
	@RequestMapping("withdraws")
	public Object withdraws(@RequestBody @Valid RechargesParam param) {
		return accountService.withdraws(param.query());
	}
	
	@ResponseBody
	@RequestMapping("user/withdraws")
	public Object userWithdraws(@RequestBody @Valid SoaParam param) {
		Query query = new Query().eq("uid", param.getUser().getId());
		return accountService.withdraws(query);
	}
	
	@ResponseBody
	@RequestMapping("adjust")
	public Object adjust(@RequestBody @Valid AdjustParam param) {
		if (NumberUtil.isZero(param.getAmount()))
			return Response.ok();
		AccountDetail detail = new AccountDetail(param.getMemo(), GatlinBizType.SYSTEM_ADJUST.mark());
		boolean incr = param.getAmount().compareTo(BigDecimal.ZERO) > 0;
		switch (param.getField()) {
		case USABLE:
			if (incr)
				detail.usableIncr(param.getOwnerType(), param.getOwner(), param.getType(), param.getAmount());
			else
				detail.usableDecr(param.getOwnerType(), param.getOwner(), param.getType(), param.getAmount());
			break;
		case FROZEN:
			if (incr)
				detail.frozenIncr(param.getOwnerType(), param.getOwner(), param.getType(), param.getAmount());
			else
				detail.frozenDecr(param.getOwnerType(), param.getOwner(), param.getType(), param.getAmount());
			break;
		default:
			throw new CodeException();
		}
		accountService.process(detail);
		return Response.ok();
	}
}
