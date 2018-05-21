package org.gatlin.soa.sinapay.manager;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.DepositRechargeState;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.model.OnlineBankPay;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.request.order.DepositRechargeRequest;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.SinaConsts;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.param.SinaRechargeParam;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.SinaRechargeDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaOrderManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SinaOrderManager.class);
	
	@Resource
	private ConfigService configService;
	@Resource
	private AccountService accountService;
	@Resource
	private SinaRechargeDao sinaRechargeDao;
	@Resource
	private SinaMemberManager sinaMemberManager;

	@Transactional
	public String depositRecharge(Recharge recharge, SinaRechargeParam param) {
		SinaUser recharger = param.isPersonal() 
				? sinaMemberManager.user(MemberType.PERSONAL, String.valueOf(recharge.getRecharger()))
				: sinaMemberManager.user(MemberType.ENTERPRISE, String.valueOf(recharge.getRechargee()));
		Assert.isTrue(SinaCode.MEMBER_UNREALNAME, null != recharger && recharger.isRealname());
		accountService.recharge(recharge);
		sinaRechargeDao.insert(EntityGenerator.newSinaRecharge(recharge, param, null, null));
		DepositRechargeRequest.Builder builder = new DepositRechargeRequest.Builder();
		builder.outTradeNo(recharge.getId());
		builder.accountType(param.getAccountType());
		OnlineBankPay pay = new OnlineBankPay();
		if (!param.isPersonal())
			pay.setCardAttribute(CardAttribute.B);
		builder.payMethod(pay, param.getAmount());
		builder.payerIp(recharge.getIp());
		builder.identityId(recharger.getSinaId());
		if (recharge.getFee().compareTo(BigDecimal.ZERO) > 0)
			builder.userFee(recharge.getFee());
		builder.returnUrl(_returnUrl(param.getUser()));
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_RECHARGE_SINA));
		DeviceType device = param.getUser().getDeviceType();
		if (device == DeviceType.MOBILE)
			builder.cashdeskAddrCategory(CashdeskAddrCategory.MOBILE);
		DepositRechargeRequest request = builder.build();
		logger.info("新浪托管充值请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositRechargeResponse response = request.sync();
		logger.info("新浪托管充值响应：{}", SerializeUtil.GSON.toJson(response));
		return response.getRedirectUrl();
	}
	
	// 充值成功之后执行代收到中间户
	@Transactional
	public SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice) {
		SinaRecharge recharge = sinaRechargeDao.queryUnique(new Query().eq("id", notice.getOuter_trade_no()).forUpdate());
		Assert.notNull(SinaCode.RECHARGE_NOT_EXIST, recharge);
		RechargeState state = RechargeState.valueOf(recharge.getState());
		DepositRechargeState cstate = DepositRechargeState.valueOf(notice.getDeposit_status());
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, state == RechargeState.PROCESSING);
		Assert.isTrue(SinaCode.UNRECOGNIZE_DATA, cstate == DepositRechargeState.FAILED || cstate == DepositRechargeState.SUCCESS);
		recharge.setState(_rechargeState(cstate).name());
		recharge.setUpdated(DateUtil.current());
		if (cstate == DepositRechargeState.FAILED) 		// 充值失败同步通知应用充值失败
			accountService.rechargeNotice(recharge.getId(), org.gatlin.soa.account.bean.enums.RechargeState.CLOSE);
		sinaRechargeDao.update(recharge);
		return recharge;
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
	
	private RechargeState _rechargeState(DepositRechargeState state) {
		switch (state) {
		case SUCCESS:
			return RechargeState.SUCCESS;
		case FAILED:
			return RechargeState.FAILED;
		default:
			throw new CodeException();
		}
	}
}
