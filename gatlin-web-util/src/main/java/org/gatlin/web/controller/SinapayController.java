package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.param.MemberActivateParam;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.SinapayCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 新浪支付网关
 * 
 * @author lynn
 */
@Controller
@RequestMapping("sinapay")
@Conditional(SinapayCondition.class)
public class SinapayController {
	
	@Resource
	private UserService userService;
	@Resource
	private SinapayMemberService sinapayMemberService;

	@ResponseBody
	@RequestMapping("member/activate")
	public Object memberActivate(@RequestBody @Valid MemberActivateParam param) {
		if (param.getType() == MemberType.ENTERPRISE) {
			
		}
		sinapayMemberService.activate(param.getTid(), param.getType(), param.meta().getIp());
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("member/realname")
	public Object memberRealname(@RequestBody @Valid RealnameParam param) {
		if (!StringUtil.hasText(param.getMobile())) {
			Query query = new Query().eq("uid", param.getUser().getId()).eq("type", UsernameType.MOBILE.mark());
			Username username = userService.username(query);
			Assert.notNull(CoreCode.PARAM_ERR, username);
			param.setMobile(username.getUsername());
		}
		String tid = String.valueOf(param.getUser().getId());
		sinapayMemberService.realname(tid, param.getRealname(), param.getIdentity(), param.meta().getIp());
		return null;
	}
}
