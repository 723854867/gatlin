package org.gatlin.soa.sinapay.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.model.message.SchedulerMessage;
import org.gatlin.core.service.MessageSender;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.CompanyAuditState;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.notice.CompanyAuditNotice;
import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindRequest;
import org.gatlin.sdk.sinapay.request.member.CompanyApplyRequest;
import org.gatlin.sdk.sinapay.request.member.ModifyBankCardMobileConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.ModifyBankCardMobileRequest;
import org.gatlin.sdk.sinapay.request.member.PwdResetRequest;
import org.gatlin.sdk.sinapay.request.member.QueryWithholdRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.request.member.WithholdRequest;
import org.gatlin.sdk.sinapay.response.BankCardBindConfirmResponse;
import org.gatlin.sdk.sinapay.response.BankCardBindResponse;
import org.gatlin.sdk.sinapay.response.BankCardUnbindResponse;
import org.gatlin.sdk.sinapay.response.QueryWithholdResponse;
import org.gatlin.sdk.sinapay.response.RedirectResponse;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.SinaBizHook;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.SinaConsts;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaCompanyAudit;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.enums.BankCardState;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyConfirmParam;
import org.gatlin.soa.sinapay.bean.param.BankCardMobileModifyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.sinapay.bean.param.CompanyBankCardModifyParam;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.SinaBankCardDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaCompanyAuditDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaUserDao;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.entity.Company;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaMemberManager {

	private static final Logger logger = LoggerFactory.getLogger(SinaMemberManager.class);
	
	@Resource
	private SinaUserDao sinaUserDao;
	@Resource
	private SinaBizHook sinaBizHook;
	@Resource
	private SinaManager sinaManager;
	@Resource
	private MessageSender messageSender;
	@Resource
	private ConfigService configService;
	@Resource
	private SinaBankCardDao sinaBankCardDao;
	@Resource
	private SinaCompanyAuditDao sinaCompanyAuditDao;
	
	@Transactional
	public UserSecurity realname(SinaUser user, RealnameParam param) {
		Assert.isTrue(SinaCode.MEMBER_ALREADY_REALNAME, !user.isRealname());
		user.setRealname(true);
		user.setUpdated(DateUtil.current());
		sinaUserDao.update(user);
		UserSecurity security = sinaBizHook.realname(param);
		RealnameRequest.Builder builder = new RealnameRequest.Builder();
		builder.certNo(param.getIdentity());
		builder.realname(param.getRealname());
		builder.clientIp(param.meta().getIp());
		builder.identityId(user.getSinaId());
		RealnameRequest request = builder.build();
		logger.info("新浪实名认证请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪实名认证响应：{}", SerializeUtil.GSON.toJson(response));
		return security;
	}
	
	@Transactional
	public String bankCardBind(BankCardBindParam param, String bankId, Geo geo) { 
		SinaUser user = user(MemberType.PERSONAL, param.getUser().getId());
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		Assert.isTrue(SinaCode.MEMBER_UNREALNAME, user.isRealname());
		SinaBank bank = sinaManager.bank(bankId);
		Assert.isTrue(SinaCode.BANK_UNSUPPORT, null != bank && bank.isValid());
		Set<String> set = new HashSet<String>();
		set.add(BankCardState.BINDED.name());
		set.add(BankCardState.BINDING.name());
		Query query = new Query().eq("bank_no", param.getBankNo()).in("state", set).forUpdate();
		SinaBankCard bankCard = sinaBankCardDao.queryUnique(query);
		Assert.isNull(SinaCode.BANK_CARD_ALREADY_BIND, bankCard);
		BankCardBindRequest.Builder builder = new BankCardBindRequest.Builder();
		String requestNo = IDWorker.INSTANCE.nextSid();
		builder.requestNo(requestNo);
		builder.identityId(user.getSinaId());
		builder.clientIp(param.meta().getIp());
		builder.bankCode(bank.getSinaId());
		builder.bankAccountNo(param.getBankNo());
		builder.province(geo.getProvince());
		builder.city(geo.getCity());
		builder.phoneNo(String.valueOf(PhoneUtil.getNationalNumber(param.getMobile())));
		if (StringUtil.hasText(param.getBranch()))
			builder.bankBranch(param.getBranch());
		BankCardBindRequest request = builder.build();
		logger.info("新浪绑卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		BankCardBindResponse response = request.sync();
		logger.info("新浪绑卡响应：{}", SerializeUtil.GSON.toJson(response));
		String ticket = response.getTicket();
		bankCard = EntityGenerator.newSinaBankCard(user, requestNo, param, geo, bank, ticket);
		sinaBankCardDao.insert(bankCard);
		int minutes = configService.config(SinaConsts.BANK_CARD_TICKET_EXPIRY);
		SchedulerMessage message = new SchedulerMessage();
		message.setAttach(bankCard.getId());
		message.setDelay(minutes * 60000l);
		messageSender.send(SinaConsts.MESSAGE_SINA_CARD_BIND_TIMEOUT, message);
		return bankCard.getId();
	}
	
	@Transactional
	public void bankCardBindTimeout(String id) {
		Query query = new Query().eq("id", id).forUpdate();
		SinaBankCard cardBind = sinaBankCardDao.queryUnique(query);
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, cardBind);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, cardBind.getState() == BankCardState.BINDING);
		cardBind.setState(BankCardState.FAILED);
		cardBind.setUpdated(DateUtil.current());
		sinaBankCardDao.update(cardBind);
	}
	
	@Transactional
	public String bankCardBindConfirm(BankCardConfirmParam param) {
		Query query = new Query().eq("id", param.getId()).forUpdate();
		SinaBankCard cardBind = sinaBankCardDao.queryUnique(query);
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, cardBind);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, cardBind.getState()== BankCardState.BINDING);
		int minutes = configService.config(SinaConsts.BANK_CARD_TICKET_EXPIRY);
		int usedCount = configService.config(SinaConsts.BANK_CARD_TICKET_MAXIMUM_USED);
		int gap = (DateUtil.current() - cardBind.getCreated()) / 60;
		Assert.isTrue(SinaCode.BANK_CARD_BIND_TICKET_INVALID, cardBind.getUsed() < usedCount && gap < minutes);
		BankCard card = EntityGenerator.newBankCard(cardBind, sinaUserDao.getByKey(cardBind.getOwner()));
		sinaBizHook.cardBind(card);
		BankCardBindConfirmRequest.Builder builder = new BankCardBindConfirmRequest.Builder();
		builder.ticket(cardBind.getTicket());
		builder.clientIp(param.meta().getIp());
		builder.validCode(param.getCaptcha());
		BankCardBindConfirmRequest request = builder.build();
		logger.info("新浪确认绑卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		BankCardBindConfirmResponse response = request.sync();
		logger.info("新浪确认绑卡响应：{}", SerializeUtil.GSON.toJson(response));
		cardBind.setCardId(card.getId());
		cardBind.setUpdated(DateUtil.current());
		cardBind.setUsed(cardBind.getUsed() + 1);
		cardBind.setSinaCardId(response.getCardId());
		cardBind.setState(BankCardState.BINDED);
		sinaBankCardDao.update(cardBind);
		return card.getId();
	}
	
	@Transactional
	public String bankCardUnbind(SoaSidParam param) {
		Query query = new Query().eq("card_id", param.getId());
		SinaBankCard bankCard = sinaBankCardDao.queryUnique(query);
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, bankCard);
		Assert.isTrue(SinaCode.BANK_CARD_UNBIND, bankCard.getState() == BankCardState.BINDED);
		BankCardUnbindRequest.Builder builder = new BankCardUnbindRequest.Builder();
		builder.identityId(bankCard.getOwner());
		builder.clientIp(param.meta().getIp());
		builder.cardId(bankCard.getSinaCardId());
		BankCardUnbindRequest request = builder.build();
		logger.info("新浪解绑银行卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		BankCardUnbindResponse response = request.sync();
		logger.info("新浪解绑银行卡响应：{}", SerializeUtil.GSON.toJson(response));
		bankCard.setUsed(0);
		bankCard.setTicket(response.getTicket());
		bankCard.setUpdated(DateUtil.current());
		sinaBankCardDao.update(bankCard);
		return bankCard.getId();
	}
	
	@Transactional
	public void bankCardUnbindConfirm(BankCardConfirmParam param) {
		SinaBankCard bankCard = sinaBankCardDao.getByKey(param.getId());
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, bankCard);
		Assert.isTrue(SinaCode.BANK_CARD_UNBIND, null != bankCard.getCardId() && null != bankCard.getSinaCardId());
		int minutes = configService.config(SinaConsts.BANK_CARD_TICKET_EXPIRY);
		int usedCount = configService.config(SinaConsts.BANK_CARD_TICKET_MAXIMUM_USED);
		int gap = (DateUtil.current() - bankCard.getUpdated()) / 60;
		Assert.isTrue(SinaCode.BANK_CARD_BIND_TICKET_INVALID, bankCard.getUsed() < usedCount && gap < minutes);
		BankCardUnbindConfirmRequest.Builder builder = new BankCardUnbindConfirmRequest.Builder();
		builder.identityId(bankCard.getOwner());
		builder.ticket(bankCard.getTicket());
		builder.validCode(param.getCaptcha());
		builder.clientIp(param.meta().getIp());
		sinaBizHook.cardUnbind(bankCard.getCardId());
		bankCard.setUsed(bankCard.getUsed() + 1);
		bankCard.setState(BankCardState.UNBIND);
		bankCard.setUpdated(DateUtil.current());
		sinaBankCardDao.update(bankCard);
		BankCardUnbindConfirmRequest request = builder.build();
		logger.info("新浪确认解绑银行卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪确认解绑银行卡响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	@Transactional
	public String bankCardMobileModify(BankCardMobileModifyParam param) { 
		SinaBankCard bankCard = sinaBankCardDao.queryUnique(new Query().eq("card_id", param.getCardId()));
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, bankCard);
		Assert.isTrue(SinaCode.BANK_CARD_BIND_NOT_EXIST, bankCard.getState() == BankCardState.BINDED);
		ModifyBankCardMobileRequest.Builder builder = new ModifyBankCardMobileRequest.Builder();
		builder.identityId(bankCard.getOwner());
		builder.cardId(bankCard.getSinaCardId());
		builder.phoneNo(String.valueOf(PhoneUtil.getNationalNumber(param.getMobile())));
		ModifyBankCardMobileRequest request = builder.build();
		logger.info("新浪修改银行预留手机号请求：{}", SerializeUtil.GSON.toJson(request.params()));		
		BankCardBindResponse response = request.sync();
		logger.info("新浪修改银行预留手机号响应：{}", SerializeUtil.GSON.toJson(response));
		bankCard.setTicket(response.getTicket());
		sinaBankCardDao.update(bankCard);
		return bankCard.getId();
	}
	
	// 此处需要客户端配合再次传mobile：有点小瑕疵，不过一般问题不大。
	@Transactional
	public void bankCardMobileModifyConfirm(BankCardMobileModifyConfirmParam param) {
		SinaBankCard bankCard = sinaBankCardDao.getByKey(param.getId());
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, bankCard);
		ModifyBankCardMobileConfirmRequest.Builder builder = new ModifyBankCardMobileConfirmRequest.Builder();
		builder.validCode(param.getCaptcha());
		builder.ticket(bankCard.getTicket());
		ModifyBankCardMobileConfirmRequest request = builder.build();
		logger.info("新浪确认修改银行预留手机号请求：{}", SerializeUtil.GSON.toJson(request.params()));		
		BankCardBindConfirmResponse response = request.sync();
		logger.info("新浪确认修改银行预留手机号响应：{}", SerializeUtil.GSON.toJson(response));
		bankCard.setSinaCardId(response.getCardId());
		bankCard.setMobile(param.getMobile());
		sinaBankCardDao.update(bankCard);
	}
	
	public boolean isWithhold(SinaUser user) {
		QueryWithholdRequest.Builder builder = new QueryWithholdRequest.Builder();
		builder.identityId(user.getSinaId());
		QueryWithholdRequest request = builder.build();
		QueryWithholdResponse response = request.sync();
		if (response.isWithholdAuthority() && user.getType() == MemberType.PERSONAL) {
			user.setWithhold(true);
			user.setUpdated(DateUtil.current());
			sinaUserDao.update(user);
		}
		return response.isWithholdAuthority();
	}
	
	public String withhold(SoaParam param) {
		SinaUser user = user(MemberType.PERSONAL, param.getUser().getId());
		Assert.isTrue(SinaCode.MEMBER_UNREALNAME, null != user && user.isRealname());
		WithholdRequest.Builder builder = new WithholdRequest.Builder();
		builder.identityId(user.getSinaId());
		builder.returnUrl(_returnUrl(param.getUser()));
		WithholdRequest request = builder.build();
		RedirectResponse response = request.sync();
		return response.getRedirectUrl();
	}
	
	@Transactional
	public void companyApply(SinaUser user, CompanyApplyParam param, Company company, SinaBank bank, Geo geo) {
		SinaCompanyAudit companyAudit = companyAudit(user.getSinaId());
		Assert.isNull(SinaCode.COMPANY_ALREADY_APPLY, companyAudit);
		user.setRealname(true);
		user.setUpdated(DateUtil.current());
		sinaUserDao.update(user);
		companyAudit = EntityGenerator.newSinaCompanyAudit(user, param, geo);
		sinaCompanyAuditDao.insert(companyAudit);
		CompanyApplyRequest.Builder builder = new CompanyApplyRequest.Builder();
		builder.auditOrderNo(companyAudit.getId());
		builder.companyName(company.getName());
		builder.identityId(user.getSinaId());
		builder.address(company.getAddress());
		builder.licenseNo(company.getIdentity());
		builder.licenseAddress(company.getLicenseAddress());
		builder.licenseExpireDate(DateUtil.getDate(DateUtil.yyyyMMdd, company.getLicenseExpiry() * 1000));
		builder.businessScope(company.getBusinessScope());
		builder.telephone(company.getTelephone());
		builder.email(company.getEmail());
		builder.organizationNo(company.getIdentity());
		builder.summary(company.getSummary());
		builder.legalPerson(company.getLegalPerson());
		builder.certNo(company.getLegalIdentity());
		builder.legalPersonPhone(String.valueOf(PhoneUtil.getNationalNumber(company.getLegalMobile())));
		builder.bankCode(bank.getSinaId());
		builder.bankAccountNo(param.getBankNo());
		builder.province(geo.getProvince());
		builder.city(geo.getCity());
		builder.bankBranch(param.getBranch());
		builder.clientIp(param.meta().getIp());
		builder.fileName(param.getFilename());
		builder.digest(param.getDigest());
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_COMPANY_AUDIT));
		CompanyApplyRequest request = builder.build();
		logger.info("新浪企业认证请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪企业认证响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	@Transactional
	public void companyApplyNotice(CompanyAuditNotice notice) { 
		Query query = new Query().eq("id", notice.getAudit_order_no()).forUpdate();
		SinaCompanyAudit companyAudit = sinaCompanyAuditDao.queryUnique(query);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, companyAudit.getState() == CompanyAuditState.PROCESSING);
		companyAudit.setState(notice.getAudit_status());
		companyAudit.setAuditMsg(StringUtil.hasText(notice.getAudit_message()) ? notice.getAudit_message() : StringUtil.EMPTY);
		if (notice.getAudit_status() == CompanyAuditState.SUCCESS) {
			SinaUser user = sinaUserDao.getByKey(companyAudit.getSinaUid());
			BankCard card = EntityGenerator.newBankCard(companyAudit, user);
			sinaBizHook.cardBind(card);
			SinaBankCard sinaBankCard = EntityGenerator.newSinaBankCard(card, companyAudit);
			sinaBankCardDao.insert(sinaBankCard);
		}
		sinaCompanyAuditDao.update(companyAudit);
	}
	
	@Transactional
	public void companyBankCardModify(CompanyBankCardModifyParam param) { 
		Query query = new Query().eq("id", param.getId()).forUpdate();
		SinaBankCard bankCard = sinaBankCardDao.queryUnique(query);
		Assert.notNull(SinaCode.BANK_CARD_UNBIND, bankCard);
		bankCard.setSinaCardId(param.getCardId());
		bankCard.setUpdated(DateUtil.current());
		sinaBankCardDao.update(bankCard);
	}
	
	public String pwdReset(SoaParam param) { 
		SinaUser user = user(MemberType.PERSONAL, param.getUser().getId());
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		PwdResetRequest.Builder builder = new PwdResetRequest.Builder();
		builder.identityId(user.getSinaId());
		builder.returnUrl(_returnUrl(param.getUser()));
		PwdResetRequest request = builder.build();
		RedirectResponse response = request.sync();
		return response.getRedirectUrl();
	}
	
	public SinaUser tryActivate(Object tid, MemberType type, String ip) {
		Query query = new Query().eq("type", type.mark()).eq("tid", tid.toString()).forUpdate();
		SinaUser user = user(query);
		if (null == user) {
			user = EntityGenerator.newSinaUser(tid.toString(), type);
			sinaUserDao.insert(user);
			ActivateRequest.Builder builder = new ActivateRequest.Builder();
			builder.clientIp(ip);
			builder.memberType(type);
			builder.identityId(user.getSinaId());
			ActivateRequest request = builder.build();
			logger.info("新浪会员激活请求：{}", SerializeUtil.GSON.toJson(request.params()));
			SinapayResponse response = request.sync();
			logger.info("新浪会员激活响应：{}", SerializeUtil.GSON.toJson(response));
			return user;
		}
		return user;
	}
	
	private String _returnUrl(User user) {
		Client client = user.getClient();
		switch (client) {
		case BROWSER:
			DeviceType device = user.getDeviceType();
			switch (device) {
			case PC:
				return configService.config(SinaConsts.URL_RETURN_BROWSER_PC);
			default:
				return configService.config(SinaConsts.URL_RETURN_BROWSER_WAP);
			}
		case ORIGINAL:			// 原生客户端范围appurl
			return configService.config(SinaConsts.URL_RETURN_ORIGINAL);
		default:
			throw new CodeException();
		}
	}
	
	public SinaUser member(String id) {
		return sinaUserDao.getByKey(id);
	}
	
	public SinaUser user(Query query) {
		return sinaUserDao.queryUnique(query);
	}
	
	public SinaUser user(MemberType type, Object tid) {
		Query query = new Query().eq("type", type.mark()).eq("tid", tid.toString());
		return user(query);
	}
	
	public List<SinaBankCard> bankCards(Query query) {
		return sinaBankCardDao.queryList(query);
	}
	
	public SinaCompanyAudit companyAudit(String sinaId) {
		Set<String> set = new HashSet<String>();
		set.add(CompanyAuditState.SUCCESS.name());
		set.add(CompanyAuditState.PROCESSING.name());
		Query query = new Query().eq("sina_uid", sinaId).in("state", set).forUpdate();
		return sinaCompanyAuditDao.queryUnique(query);
	}
}
