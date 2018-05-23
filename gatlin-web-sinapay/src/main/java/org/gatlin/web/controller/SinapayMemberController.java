package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.bean.param.MemberActivateParam;
import org.gatlin.soa.sinapay.bean.param.QueryBalanceParam;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 新浪支付会员网关
 * 
 * @author lynn
 */
@Controller
@RequestMapping("sinapay/member")
public class SinapayMemberController {
	
	@Resource
	private UserService userService;
	@Resource
	private ConfigService configService;
	@Resource
	private SinapayMemberService sinapayMemberService;

	@ResponseBody
	@RequestMapping("activate")
	public Object activate(@RequestBody @Valid MemberActivateParam param) {
		if (param.getType() == MemberType.ENTERPRISE) {
			
		}
		sinapayMemberService.activate(param.getTid(), param.getType(), param.meta().getIp());
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("realname")
	public Object realname(@RequestBody @Valid RealnameParam param) {
		if (!StringUtil.hasText(param.getMobile())) {
			Query query = new Query().eq("uid", param.getUser().getId()).eq("type", UsernameType.MOBILE.mark());
			Username username = userService.username(query);
			Assert.notNull(CoreCode.PARAM_ERR, username);
			param.setMobile(username.getUsername());
		}
		return sinapayMemberService.realname(param);
	}
	
	@ResponseBody
	@RequestMapping("bank/card/bind")
	public Object bankCardBind(@RequestBody @Valid BankCardBindParam param) {
		CfgBank bank = configService.bank(param.getBankId());
		Assert.isTrue(ConfigCode.BANK_UNSUPPORT, null != bank && bank.isValid());
		Geo geo = configService.geo(param.getCity(), false);
		Assert.hasText(CoreCode.PARAM_ERR, geo.getCity());
		return sinapayMemberService.bankCardBind(param, bank.getId(), geo);
	}
	
	@ResponseBody
	@RequestMapping("bank/card/bind/confirm")
	public Object bankCardBindConfirm(@RequestBody @Valid BankCardConfirmParam param) {
		return sinapayMemberService.bankCardBindConfirm(param);
	}
	
	@ResponseBody
	@RequestMapping("bank/card/unbind")
	public Object bankCardUnbind(@RequestBody @Valid SoaSidParam param) {
		return sinapayMemberService.bankCardUnbind(param);
	}
	
	@ResponseBody
	@RequestMapping("bank/card/unbind/confirm")
	public Object bankCardUnbindConfirm(@RequestBody @Valid BankCardConfirmParam param) {
		sinapayMemberService.bankCardUnbindConfirm(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("withhold")
	public Object withhold(@RequestBody @Valid SoaParam param) {
		return sinapayMemberService.withhold(param);
	}
	
	@ResponseBody
	@RequestMapping("query/balance")
	public Object queryBalance(@RequestBody @Valid QueryBalanceParam param) { 
		return sinapayMemberService.queryBalance(param);
	}
	
	// 查询中间账户
	@ResponseBody
	@RequestMapping("query/balance/middle")
	public Object queryBalance(@RequestBody @Valid SoaParam param) { 
		return sinapayMemberService.queryBalanceMiddle();
	}
}
