package org.gatlin.web.sina.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.param.MemberActivateParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
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
public class SinapayController {
	
	@Resource
	private SinapayMemberService sinapayMemberService;

	@ResponseBody
	@RequestMapping("member/activate")
	public Object memberActivate(@RequestBody @Valid MemberActivateParam param) {
		if (param.getType() == MemberType.ENTERPRISE) {
			
		}
		return sinapayMemberService.activate(param.getTid(), param.getType(), param.meta().getIp());
	}
	
	@ResponseBody
	@RequestMapping("member/realname")
	public Object memberRealname(@RequestBody @Valid RealnameParam param) {
		String tid = String.valueOf(param.getUser().getId());
		sinapayMemberService.realname(tid, param.getRealname(), param.getIdentity(), param.meta().getIp());
		return null;
	}
}
