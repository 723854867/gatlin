package org.gatlin.soa.sinapay.manager;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindRequest;
import org.gatlin.sdk.sinapay.request.member.QueryWithholdRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.request.member.WithholdRequest;
import org.gatlin.sdk.sinapay.response.BankCardBindConfirmResponse;
import org.gatlin.sdk.sinapay.response.BankCardBindResponse;
import org.gatlin.sdk.sinapay.response.BankCardUnbindResponse;
import org.gatlin.sdk.sinapay.response.QueryWithholdResponse;
import org.gatlin.sdk.sinapay.response.RedirectResponse;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.SinaConsts;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.param.BankCardConfirmParam;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.SinaBankCardDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaUserDao;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaMemberManager {

	private static final Logger logger = LoggerFactory.getLogger(SinaMemberManager.class);
	
	@Resource
	private SinaUserDao sinaUserDao;
	@Resource
	private UserService userService;
	@Resource
	private SinaManager sinaManager;
	@Resource
	private ConfigService configService;
	@Resource
	private SinaBankCardDao sinaBankCardDao;
	@Resource
	private BankCardService bankCardService;
	
	@Transactional
	public String activate(String tid, MemberType type, String ip) {
		SinaUser user = null;
		try {
			user = EntityGenerator.newSinaUser(tid, type);
			sinaUserDao.insert(user);
		} catch (DuplicateKeyException e) {
			throw new CodeException(SinaCode.MEMBER_EXIST);
		}
		ActivateRequest.Builder builder = new ActivateRequest.Builder();
		builder.clientIp(ip);
		builder.identityId(user.getSinaId());
		ActivateRequest request = builder.build();
		logger.info("新浪会员激活请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪会员激活响应：{}", SerializeUtil.GSON.toJson(response));
		return user.getSinaId();
	}
	
	@Transactional
	public UserSecurity realname(RealnameParam param) {
		String tid = String.valueOf(param.getUser().getId());
		Query query = new Query().eq("type", MemberType.PERSONAL.mark()).eq("tid", tid).forUpdate();
		SinaUser user = user(query);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		Assert.isTrue(SinaCode.MEMBER_ALREADY_REALNAME, !user.isRealname());
		user.setRealname(true);
		user.setUpdated(DateUtil.current());
		sinaUserDao.update(user);
		UserSecurity security = userService.realname(param);
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
		Query query = new Query().eq("type", MemberType.PERSONAL.mark()).eq("tid", param.getUser().getId());
		SinaUser user = user(query);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		Assert.isTrue(SinaCode.MEMBER_UNREALNAME, user.isRealname());
		SinaBank bank = sinaManager.bank(bankId);
		Assert.isTrue(SinaCode.BANK_UNSUPPORT, null != bank && bank.isValid());
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
		SinaBankCard bankCard = EntityGenerator.newSinaBankCard(user, requestNo, param, geo, bank, ticket);
		sinaBankCardDao.insert(bankCard);
		return bankCard.getId();
	}
	
	@Transactional
	public String bankCardBindConfirm(BankCardConfirmParam param) {
		int minutes = configService.config(SinaConsts.BANK_CARD_TICKET_EXPIRY);
		int usedCount = configService.config(SinaConsts.BANK_CARD_TICKET_MAXIMUM_USED);
		SinaBankCard cardBind = sinaBankCardDao.getByKey(param.getId());
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, cardBind);
		int gap = (DateUtil.current() - cardBind.getCreated()) / 60;
		Assert.isTrue(SinaCode.BANK_CARD_BIND_TICKET_INVALID, cardBind.getUsed() < usedCount && gap < minutes);
		BankCard card = EntityGenerator.newBankCard(cardBind);
		bankCardService.cardBind(card);
		BankCardBindConfirmRequest.Builder builder = new BankCardBindConfirmRequest.Builder();
		builder.ticket(cardBind.getTicket());
		builder.clientIp(param.meta().getIp());
		builder.validCode(param.getCaptcha());
		BankCardBindConfirmRequest request = builder.build();
		logger.info("新浪确认绑卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		BankCardBindConfirmResponse response = request.sync();
		logger.info("新浪确认绑卡响应：{}", SerializeUtil.GSON.toJson(response));
		cardBind.setCardId(card.getId());
		cardBind.setSinaCardId(response.getCardId());
		cardBind.setUpdated(DateUtil.current());
		cardBind.setUsed(cardBind.getUsed() + 1);
		sinaBankCardDao.update(cardBind);
		return card.getId();
	}
	
	@Transactional
	public String bankCardUnbind(SoaSidParam param) {
		Query query = new Query().eq("card_id", param.getId());
		SinaBankCard bankCard = sinaBankCardDao.queryUnique(query);
		Assert.notNull(SinaCode.BANK_CARD_BIND_NOT_EXIST, bankCard);
		Assert.isTrue(SinaCode.BANK_CARD_UNBIND, null != bankCard.getCardId() && null != bankCard.getSinaCardId());
		BankCardUnbindRequest.Builder builder = new BankCardUnbindRequest.Builder();
		builder.identityId(bankCard.getSinaUid());
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
		builder.identityId(bankCard.getSinaUid());
		builder.ticket(bankCard.getTicket());
		builder.validCode(param.getCaptcha());
		builder.clientIp(param.meta().getIp());
		bankCardService.cardUnbind(bankCard.getCardId());
		bankCard.setCardId(null);
		bankCard.setSinaCardId(null);
		bankCard.setUsed(bankCard.getUsed() + 1);
		bankCard.setUpdated(DateUtil.current());
		sinaBankCardDao.update(bankCard);
		BankCardUnbindConfirmRequest request = builder.build();
		logger.info("新浪确认解绑银行卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪确认解绑银行卡响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	public boolean isWithhold(MemberType type, String tid) {
		SinaUser user = user(type, tid);
		QueryWithholdRequest.Builder builder = new QueryWithholdRequest.Builder();
		builder.identityId(user.getSinaId());
		QueryWithholdRequest request = builder.build();
		QueryWithholdResponse response = request.sync();
		return response.isWithholdAuthority();
	}
	
	public String withhold(SoaParam param) {
		SinaUser user = user(MemberType.PERSONAL, param.getUser().getId());
		Assert.isTrue(SinaCode.MEMBER_UNREALNAME, null != user && user.isRealname());
		WithholdRequest.Builder builder = new WithholdRequest.Builder();
		builder.identityId(user.getSinaId());
		WithholdRequest request = builder.build();
		RedirectResponse response = request.sync();
		return response.getRedirectUrl();
	}
	
	public SinaUser user(Query query) {
		return sinaUserDao.queryUnique(query);
	}
	
	public SinaUser user(MemberType type, Object tid) {
		Query query = new Query().eq("type", type.mark()).eq("tid", tid.toString());
		return user(query);
	}
}
