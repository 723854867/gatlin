package org.gatlin.soa.sinapay.service;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.SinapayConfig;
import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.model.AccountMiddleTips;
import org.gatlin.sdk.sinapay.notice.CompanyAuditNotice;
import org.gatlin.sdk.sinapay.request.member.QueryBalanceRequest;
import org.gatlin.sdk.sinapay.request.member.QueryMiddleBalanceRequest;
import org.gatlin.sdk.sinapay.response.QueryBalanceResponse;
import org.gatlin.sdk.sinapay.response.QueryTradeRelatedResponse;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaCompanyAudit;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.model.BalanceInfo;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyBankCardModifyParam;
import org.gatlin.soa.sinapay.bean.param.QueryBalanceParam;
import org.gatlin.soa.sinapay.manager.SinaManager;
import org.gatlin.soa.sinapay.manager.SinaMemberManager;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("sinapayMemberService")
public class SinapayMemberServiceImpl implements SinapayMemberService {
	
	private static final Logger logger = LoggerFactory.getLogger(SinapayMemberServiceImpl.class);
	
	@Resource
	private SinaManager sinaManager;
	@Resource
	private ConfigService configService;
	@Resource
	private SinaMemberManager sinaMemberManager;

	@Override
	public UserSecurity realname(RealnameParam param) {
		SinaUser user = sinaMemberManager.tryActivate(param.getUser().getId(), MemberType.PERSONAL, param.meta().getIp());
		return sinaMemberManager.realname(user, param);
	}
	
	@Override
	public String bankCardBind(BankCardBindParam param, String bankId, Geo geo) {
		return sinaMemberManager.bankCardBind(param, bankId, geo);
	}
	
	@Override
	public void bankCardBindTimeout(String id) {
		sinaMemberManager.bankCardBindTimeout(id);
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
	public Pager<SinaBankCard> bankCards(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<SinaBankCard>(sinaMemberManager.bankCards(query));
	}
	
	@Override
	public SinaUser member(String id) {
		return sinaMemberManager.member(id);
	}
	
	@Override
	public SinaUser user(Object tid, MemberType type) {
		return sinaMemberManager.user(type, tid);
	}
	
	@Override
	public boolean isWithhold(MemberType type, Object tid) {
		SinaUser user = sinaMemberManager.user(type, tid);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		if (user.isWithhold())
			return true;
		return sinaMemberManager.isWithhold(user);
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
		if (param.getIdentityType() == MemberIdentityType.MEMBER_ID) {
			builder.accountType(AccountType.BASIC);
			builder.identityId(SinapayConfig.partnerId());
		}
		QueryBalanceRequest request = builder.build();
		logger.info("新浪查询用户余额请求：{}", SerializeUtil.GSON.toJson(request.params()));
		QueryBalanceResponse response = request.sync();
		logger.info("新浪查询用户余额响应：{}", SerializeUtil.GSON.toJson(response));
		return new BalanceInfo(response);
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
		SinaUser user = sinaMemberManager.tryActivate(company.getId(), MemberType.ENTERPRISE, param.meta().getIp());
		sinaMemberManager.companyApply(user, param, company, bank, geo);
	}
	
	@Override
	public void companyApplyNotice(CompanyAuditNotice notice) {
		sinaMemberManager.companyApplyNotice(notice);
	}
	
	@Override
	public void companyBankCardModify(CompanyBankCardModifyParam param) {
		sinaMemberManager.companyBankCardModify(param);
	}
	
	@Override
	public String pwdReset(SoaParam param) {
		return sinaMemberManager.pwdReset(param);
	}
	
	@Override
	public String bankCardMobileModify(BankCardMobileModifyParam param) {
		return sinaMemberManager.bankCardMobileModify(param);
	}
	
	@Override
	public void bankCardMobileModifyConfirm(BankCardMobileModifyConfirmParam param) {
		sinaMemberManager.bankCardMobileModifyConfirm(param);
	}
	
	@Override
	public SinaCompanyAudit companyAudit(int companyId) {
		SinaUser member = sinaMemberManager.user(MemberType.ENTERPRISE, companyId);
		return null == member ? null : sinaMemberManager.companyAudit(member.getSinaId());
	}
	
	@Override
	public String pwdSet(SoaParam param, MemberType type, Object tid) {
		return sinaMemberManager.pwdSet(param, type, tid);
	}
	
	@Override
	public boolean queryPwdSet(MemberType type, Object tid) {
		return sinaMemberManager.isPwdSet(type, tid);
	}
	
	@Override
	public QueryTradeRelatedResponse queryRelative(String id) {
		return sinaMemberManager.queryTradeResponse(id);
	}
}
