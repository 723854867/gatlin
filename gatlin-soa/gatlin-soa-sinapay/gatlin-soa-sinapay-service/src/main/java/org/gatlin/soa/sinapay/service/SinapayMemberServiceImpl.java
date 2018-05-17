package org.gatlin.soa.sinapay.service;

import javax.annotation.Resource;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.manager.SinaUserManager;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.springframework.stereotype.Service;

@Service("sinapayMemberService")
public class SinapayMemberServiceImpl implements SinapayMemberService {
	
	@Resource
	private SinaUserManager sinaUserManager;

	@Override
	public String activate(String tid, MemberType type, String ip) {
		return sinaUserManager.activate(tid, type, ip);
	}
	
	@Override
	public UserSecurity realname(RealnameParam param) {
		return sinaUserManager.realname(param);
	}
	
	@Override
	public String bankCardBind(BankCardBindParam param, String bankId, Geo geo) {
		return sinaUserManager.bankCardBind(param, bankId, geo);
	}
	
	@Override
	public String bankCardBindConfirm(BankCardConfirmParam param) {
		return sinaUserManager.bankCardBindConfirm(param);
	}
	
	@Override
	public String bankCardUnbind(SoaSidParam param) {
		return sinaUserManager.bankCardUnbind(param);
	}
	
	@Override
	public void bankCardUnbindConfirm(BankCardConfirmParam param) {
		sinaUserManager.bankCardUnbindConfirm(param);
	}
	
	@Override
	public SinaUser user(String tid, MemberType type) {
		return sinaUserManager.user(new Query().eq("tid", tid).eq("type", type.mark()));
	}
}
