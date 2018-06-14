package org.gatlin.web.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.ConfigCode;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyBankCardModifyParam;
import org.gatlin.soa.sinapay.bean.param.QueryBalanceParam;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.SinapayChecker;
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
	private SinapayChecker sinapayChecker;
	@Resource
	private BankCardService bankCardService;
	@Resource
	private SinapayMemberService sinapayMemberService;

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
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());
		CfgBank bank = configService.bank(param.getBankId());
		Assert.isTrue(ConfigCode.BANK_UNSUPPORT, null != bank && bank.isValid());
		Geo geo = configService.geo(param.getCity(), false);
		Assert.hasText(CoreCode.PARAM_ERR, geo.getCity());
		return sinapayMemberService.bankCardBind(param, bank.getId(), geo);
	}

	@ResponseBody
	@RequestMapping("bank/card/bind/confirm")
	public Object bankCardBindConfirm(@RequestBody @Valid BankCardConfirmParam param) {
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());
		return sinapayMemberService.bankCardBindConfirm(param);
	}

	@ResponseBody
	@RequestMapping("bank/card/unbind")
	public Object bankCardUnbind(@RequestBody @Valid SoaSidParam param) {
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());
		return sinapayMemberService.bankCardUnbind(param);
	}

	@ResponseBody
	@RequestMapping("bank/card/unbind/confirm")
	public Object bankCardUnbindConfirm(@RequestBody @Valid BankCardConfirmParam param) {
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());
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
		if (null != param.getUid()) {
			SinaUser user = sinapayMemberService.user(param.getUid(), MemberType.PERSONAL);
			if (null == user)
				return BigDecimal.ZERO;
			param.setIdentity(user.getSinaId());
		}
		return sinapayMemberService.queryBalance(param);
	}

	// 查询中间账户
	@ResponseBody
	@RequestMapping("query/balance/middle")
	public Object queryBalance(@RequestBody @Valid SoaParam param) {
		return sinapayMemberService.queryBalanceMiddle();
	}

	// 修改企业会员绑卡信息
	@ResponseBody
	@RequestMapping("company/bank/card/modify")
	public Object companyBankCardModify(@RequestBody @Valid CompanyBankCardModifyParam param) {
		sinapayMemberService.companyBankCardModify(param);
		return Response.ok();
	}
	
	// 修改密码
	@ResponseBody
	@RequestMapping("pwd/reset")
	public Object pwdReset(@RequestBody @Valid SoaParam param) { 
		sinapayChecker.checkWithhold(MemberType.PERSONAL, param.getUser().getId());
		return sinapayMemberService.pwdReset(param);
	}
	
	// 修改银行卡预留手机号
	@ResponseBody
	@RequestMapping("bank/card/mobile/modify")
	public Object bankCardMobileModify(@RequestBody @Valid BankCardMobileModifyParam param) { 
		return sinapayMemberService.bankCardMobileModify(param);
	}
	
	// 确认修改银行预留手机号
	@ResponseBody
	@RequestMapping("bank/card/mobile/modify/confirm")
	public Object bankCardMobileModifyConfirm(@RequestBody @Valid BankCardMobileModifyConfirmParam param) { 
		sinapayMemberService.bankCardBindConfirm(param);
		return Response.ok();
	}
	
	// 获取资方审核信息
	@ResponseBody
	@RequestMapping("company/audit")
	public Object companyAudit(@RequestBody @Valid SoaIdParam param) {
		return sinapayMemberService.companyAudit(param.getId());
	}
}
