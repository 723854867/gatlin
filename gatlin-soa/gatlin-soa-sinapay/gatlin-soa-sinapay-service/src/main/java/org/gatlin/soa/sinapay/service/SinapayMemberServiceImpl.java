package org.gatlin.soa.sinapay.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.model.AccountMiddleTips;
import org.gatlin.sdk.sinapay.notice.CompanyAuditNotice;
import org.gatlin.sdk.sinapay.request.member.QueryBalanceRequest;
import org.gatlin.sdk.sinapay.request.member.QueryMiddleBalanceRequest;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.model.BalanceInfo;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyBankCardModifyParam;
import org.gatlin.soa.sinapay.bean.param.QueryBalanceParam;
import org.gatlin.soa.sinapay.manager.SinaManager;
import org.gatlin.soa.sinapay.manager.SinaMemberManager;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.springframework.stereotype.Service;

@Service("sinapayMemberService")
public class SinapayMemberServiceImpl implements SinapayMemberService {
	
	@Resource
	private SinaManager sinaManager;
	@Resource
	private ConfigService configService;
	@Resource
	private SinaMemberManager sinaMemberManager;

	@Override
	public UserSecurity realname(RealnameParam param) {
		return sinaMemberManager.realname(param);
	}
	
	@Override
	public String bankCardBind(BankCardBindParam param, String bankId, Geo geo) {
		return sinaMemberManager.bankCardBind(param, bankId, geo);
	}
	
	@Override
	public String bankCardBindConfirm(BankCardConfirmParam param) {
		return sinaMemberManager.bankCardBindConfirm(param);
	}
	
	@Override
	public String bankCardUnbind(SoaSidParam param) {
		return sinaMemberManager.bankCardUnbind(param);
	}
	
	@Override
	public void bankCardUnbindConfirm(BankCardConfirmParam param) {
		sinaMemberManager.bankCardUnbindConfirm(param);
	}
	
	@Override
	public SinaUser user(String tid, MemberType type) {
		return sinaMemberManager.user(new Query().eq("tid", tid).eq("type", type.mark()));
	}
	
	@Override
	public boolean isWithhold(MemberType type, String tid) {
		return sinaMemberManager.isWithhold(type, tid);
	}
	
	@Override
	public String withhold(SoaParam param) {
		return sinaMemberManager.withhold(param);
	}
	
	@Override
	public BalanceInfo queryBalance(QueryBalanceParam param) {
		QueryBalanceRequest.Builder builder = new QueryBalanceRequest.Builder();
		builder.identityId(param.getIdentity());
		builder.accountType(param.getAccountType());
		builder.identityType(param.getIdentityType());
		QueryBalanceRequest request = builder.build();
		return new BalanceInfo(request.sync());
	}
	
	@Override
	public List<AccountMiddleTips> queryBalanceMiddle() {
		QueryMiddleBalanceRequest.Builder builder = new QueryMiddleBalanceRequest.Builder();
		QueryMiddleBalanceRequest request = builder.build();
		return request.sync().getList();
	}
	
	@Override
	public void companyApply(CompanyApplyParam param, Company company) {
		SinaBank bank = sinaManager.bank(param.getBankId());
		Assert.isTrue(SinaCode.BANK_UNSUPPORT, null != bank && bank.isValid());
		Geo geo = configService.geo(param.getCity(), false);
		Assert.hasText(CoreCode.PARAM_ERR, geo.getCity());
		Assert.hasText(CoreCode.PARAM_ERR, geo.getProvince());
		sinaMemberManager.companyApply(param, company, bank, geo);
	}
	
	@Override
	public void companyApplyNotice(CompanyAuditNotice notice) {
		sinaMemberManager.companyApplyNotice(notice);
	}
	
	@Override
	public void companyBankCardModify(CompanyBankCardModifyParam param) {
		sinaMemberManager.companyBankCardModify(param);
	}
}
