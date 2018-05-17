package org.gatlin.soa.sinapay.manager;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.response.BankCardBindResponse;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.entity.LogBankCardBind;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.LogBanCardBindDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaUserDao;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaUserManager {

	private static final Logger logger = LoggerFactory.getLogger(SinaUserManager.class);
	
	@Resource
	private SinaUserDao sinaUserDao;
	@Resource
	private UserService userService;
	@Resource
	private SinaManager sinaManager;
	@Resource
	private LogBanCardBindDao logBanCardBindDao;
	
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
		builder.clientIp(param.meta().get_id());
		builder.identityId(user.getSinaId());
		RealnameRequest request = builder.build();
		logger.info("新浪实名认证请求：{}", SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		logger.info("新浪实名认证响应：{}", SerializeUtil.GSON.toJson(response));
		return security;
	}
	
	@Transactional
	public void bankCardBind(BankCardBindParam param, String bankId, Geo geo) { 
		Query query = new Query().eq("type", MemberType.PERSONAL.mark()).eq("tid", param.getUser().getId());
		SinaUser user = user(query);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		Assert.isTrue(SinaCode.MEMBER_ALREADY_REALNAME, !user.isRealname());
		SinaBank bank = sinaManager.bank(bankId);
		Assert.isTrue(SinaCode.BANK_UNSUPPORT, null != bank && bank.isValid());
		LogBankCardBind log = new LogBankCardBind();
		log.setId(IDWorker.INSTANCE.nextSid());
		BankCardBindRequest.Builder builder = new BankCardBindRequest.Builder();
		builder.requestNo(log.getId());
		builder.identityId(user.getSinaId());
		builder.clientIp(param.meta().getIp());
		builder.bankCode(bank.getSinaId());
		builder.bankAccountNo(param.getBankNo());
		builder.province(geo.getProvince());
		builder.city(geo.getCity());
		builder.phoneNo(param.getMobile());
		if (StringUtil.hasText(param.getBranch()))
			builder.brankBranch(param.getBranch());
		log.setParams(SerializeUtil.GSON.toJson(builder));
		logBanCardBindDao.insert(log);
		BankCardBindRequest request = builder.build();
		logger.info("新浪绑卡请求：{}", SerializeUtil.GSON.toJson(request.params()));
		BankCardBindResponse response = request.sync();
		logger.info("新浪绑卡响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	public SinaUser user(Query query) {
		return sinaUserDao.queryUnique(query);
	}
}
