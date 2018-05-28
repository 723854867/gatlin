package org.gatlin.soa.sinapay.manager;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.DepositRechargeState;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.bean.enums.TradeState;
import org.gatlin.sdk.sinapay.bean.enums.WithdrawState;
import org.gatlin.sdk.sinapay.bean.model.BalancePay;
import org.gatlin.sdk.sinapay.bean.model.OnlineBankPay;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.sdk.sinapay.request.order.DepositCollectRequest;
import org.gatlin.sdk.sinapay.request.order.DepositPayRequest;
import org.gatlin.sdk.sinapay.request.order.DepositRechargeRequest;
import org.gatlin.sdk.sinapay.request.order.DepositWithdrawRequest;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;
import org.gatlin.sdk.sinapay.response.DepositResponse;
import org.gatlin.sdk.sinapay.response.DepositWithdrawResponse;
import org.gatlin.soa.account.api.AccountService;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.model.WithdrawContext;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.bean.param.WithdrawParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.SinaConsts;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaPay;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.CollectType;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.enums.SinaWithdrawState;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.SinaCollectDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaPayDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaRechargeDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaWithdrawDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaOrderManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SinaOrderManager.class);
	
	@Resource
	private SinaPayDao sinaPayDao;
	@Resource
	private ConfigService configService;
	@Resource
	private AccountService accountService;
	@Resource
	private SinaCollectDao sinaCollectDao;
	@Resource
	private SinaWithdrawDao sinaWithdrawDao;
	@Resource
	private SinaRechargeDao sinaRechargeDao;
	@Resource
	private SinaMemberManager sinaMemberManager;
	
	// ********************* 充值  *********************

	@Transactional
	public String depositRecharge(Recharge recharge, RechargeParam param) {
		TargetType rechargerType = TargetType.match(recharge.getRechargerType());
		AccountType accountType = rechargerType == TargetType.COMPANY ? AccountType.BASIC : AccountType.SAVING_POT;
		SinaUser recharger = sinaMemberManager.user(rechargerType == TargetType.COMPANY ? MemberType.ENTERPRISE : MemberType.PERSONAL, recharge.getRecharger());
		String rechargee = StringUtil.EMPTY;
		accountService.recharge(recharge);
		sinaRechargeDao.insert(EntityGenerator.newSinaRecharge(recharge, param, rechargerType, recharger.getSinaId(), param.getRechargeeType(), rechargee));
		DepositRechargeRequest.Builder builder = new DepositRechargeRequest.Builder();
		builder.outTradeNo(recharge.getId());
		builder.accountType(accountType);
		OnlineBankPay pay = new OnlineBankPay();
		if (rechargerType == TargetType.COMPANY)			// 对公充值则卡属性为对公
			pay.setCardAttribute(CardAttribute.B);
		builder.payMethod(pay, param.getAmount());
		builder.payerIp(recharge.getIp());
		builder.identityId(recharger.getSinaId());
		if (recharge.getFee().compareTo(BigDecimal.ZERO) > 0)
			builder.userFee(recharge.getFee());
		builder.returnUrl(_returnUrl(param.getUser()));
		builder.depositCloseTime("15m");
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
	
	/**
	 * 充值待收
	 */
	@Transactional
	public SinaCollect rechargeCollect(String id, DepositRechargeNotice notice) {
		SinaRecharge recharge = sinaRechargeDao.queryUnique(new Query().eq("id", id).forUpdate());
		Assert.notNull(SinaCode.RECHARGE_NOT_EXIST, recharge);
		RechargeState state = RechargeState.valueOf(recharge.getState());
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, state == RechargeState.WAIT_RECALL);
		recharge.setState(RechargeState.RECALLING.name());
		recharge.setUpdated(DateUtil.current());
		sinaRechargeDao.update(recharge);
		SinaCollect collect = EntityGenerator.newSinaCollect(CollectType.RECHARGE, recharge.getId());
		sinaCollectDao.insert(collect);
		DepositCollectRequest.Builder builder = new DepositCollectRequest.Builder();
		builder.payerId(recharge.getRecharger());
		builder.payerIp(notice.meta().getIp());
		builder.summary("充值待收");
		builder.outTradeNo(collect.getId());
		builder.outTradeCode(OutTradeCode.COLLECT_INVEST);
		BalancePay balancePay = new BalancePay();
		balancePay.setAccountType(AccountType.SAVING_POT);
		builder.paymethod(balancePay, recharge.getAmount());
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_COLLECT_SINA));
		DepositCollectRequest request = builder.build();
		logger.info("新浪充值待收请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪充值待收响应：{}", SerializeUtil.GSON.toJson(response));
		return collect;
	}
	
	// 待收回调
	@Transactional
	public SinaCollect collectNotice(TradeNotice notice) {
		Query query = new Query().eq("id", notice.getOuter_trade_no()).forUpdate();
		SinaCollect collect = sinaCollectDao.queryUnique(query);
		Assert.notNull(SinaCode.RECHARGE_COLLECT_NOT_EXIST, collect);
		TradeState state = TradeState.valueOf(collect.getState());
		TradeState cstate = TradeState.valueOf(notice.getTrade_status());
		switch (state) {
		case WAIT_PAY:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, cstate == TradeState.PAY_FINISHED || cstate == TradeState.TRADE_FAILED || cstate == TradeState.TRADE_FINISHED || cstate == TradeState.TRADE_CLOSED);
			break;
		case PAY_FINISHED:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, cstate == TradeState.TRADE_FINISHED || cstate == TradeState.TRADE_FAILED);
			break;
		default:
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		CollectType type = CollectType.valueOf(collect.getType());
		query = new Query().eq("id", collect.getTid()).forUpdate();
		switch (type) {
		case RECHARGE:						// 充值待收
			SinaRecharge recharge = sinaRechargeDao.queryUnique(query);
			switch (cstate) {
			case TRADE_FAILED:
			case TRADE_CLOSED:
				recharge.setState(RechargeState.WAIT_RECALL.name());
				recharge.setUpdated(DateUtil.current());
				sinaRechargeDao.update(recharge);
				break;
			case TRADE_FINISHED:
				recharge.setState(RechargeState.SUCCESS.name());
				recharge.setUpdated(DateUtil.current());
				sinaRechargeDao.update(recharge);
				accountService.rechargeNotice(recharge.getId(), org.gatlin.soa.account.bean.enums.RechargeState.SUCCESS);
				break;
			default:
				break;
			}
			break;
		case WITHDRAW_FAILED:						// 提现失败待收
			SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(query);
			switch (cstate) {
			case TRADE_FAILED:
			case TRADE_CLOSED:
				withdraw.setState(SinaWithdrawState.FAILED.name());
				withdraw.setUpdated(DateUtil.current());
				sinaWithdrawDao.update(withdraw);
				break;
			case TRADE_FINISHED:
				withdraw.setState(SinaWithdrawState.RECALLED.name());
				withdraw.setUpdated(DateUtil.current());
				sinaWithdrawDao.update(withdraw);
				accountService.withdrawNotice(withdraw.getId(), false);
				break;
			default:
				break;
			}
			break;
		default:
			throw new CodeException();
		}
		collect.setState(notice.getTrade_status());
		collect.setUpdated(DateUtil.current());
		sinaCollectDao.update(collect);
		return collect;
	}
	
	// ********************* 充值  *********************
	
	// 个人会员提现
	@Transactional
	public String withdrawPay(WithdrawParam param, WithdrawContext context) { 
		SinaUser user = sinaMemberManager.user(MemberType.PERSONAL, param.getUser().getId());
		Assert.isTrue(SinaCode.MEMBER_NOT_EXIST, null != user);
		Withdraw withdraw = accountService.withdraw(param, context);
		SinaPay pay = EntityGenerator.newSinaPay(withdraw);
		sinaPayDao.insert(pay);
		sinaWithdrawDao.insert(EntityGenerator.newSinaWithdraw(withdraw, user, AccountType.SAVING_POT));
		DepositPayRequest.Builder builder = new DepositPayRequest.Builder();
		builder.outTradeNo(pay.getId());
		builder.accountType(AccountType.SAVING_POT);
		builder.payeeIdentityId(user.getSinaId());
		builder.amount(param.getAmount());
		builder.summary("个人提现代付");
		builder.userIp(param.meta().getIp());
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_WITHDRAW_PAY_SINA));
		DepositPayRequest request = builder.build();
		logger.info("新浪提个人现代付请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪提个人现代付响应：{}", SerializeUtil.GSON.toJson(response));
		return withdraw.getId();
	}
	
	@Transactional
	public void withdrawPayNotice(TradeNotice notice) { 
		Query query = new Query().eq("id", notice.getOuter_trade_no()).forUpdate();
		SinaPay pay = sinaPayDao.queryUnique(query);
		Assert.notNull(SinaCode.WITHDRAW_PAY_NOT_EXIST, pay);
		TradeState state = TradeState.valueOf(pay.getState());
		TradeState cstate = TradeState.valueOf(notice.getTrade_status());
		switch (state) {
		case WAIT_PAY:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, cstate == TradeState.PAY_FINISHED || cstate == TradeState.TRADE_FAILED || cstate == TradeState.TRADE_FINISHED || cstate == TradeState.TRADE_CLOSED);
			break;
		case PAY_FINISHED:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, cstate == TradeState.TRADE_FINISHED || cstate == TradeState.TRADE_FAILED);
			break;
		default:
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		query = new Query().eq("id", pay.getWithdrawId()).forUpdate();
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(query);
		switch (cstate) {
		case TRADE_FAILED:
		case TRADE_CLOSED:
			withdraw.setState(SinaWithdrawState.PAY_FAILED.name());
			withdraw.setUpdated(DateUtil.current());
			sinaWithdrawDao.update(withdraw);
			break;
		case TRADE_FINISHED:
			withdraw.setState(SinaWithdrawState.PAYED.name());
			withdraw.setUpdated(DateUtil.current());
			sinaWithdrawDao.update(withdraw);
			break;
		default:
			break;
		}
		pay.setState(notice.getTrade_status());
		pay.setUpdated(DateUtil.current());
		sinaPayDao.update(pay);
	}
	
	@Transactional
	public String withdraw(SoaSidParam param) {
		Query query = new Query().eq("id", param.getId()).forUpdate();
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(query);
		Assert.notNull(SinaCode.WITHDRAW_NOT_EXIST, withdraw);
		SinaWithdrawState state = SinaWithdrawState.valueOf(withdraw.getState());
		if (state != SinaWithdrawState.PAYED) {
			if (state == SinaWithdrawState.WAIT_PAY)
				throw new CodeException(SinaCode.WITHDRAW_UNPAYED);
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		withdraw.setState(SinaWithdrawState.WITHDRAWING.name());
		withdraw.setUpdated(DateUtil.current());
		sinaWithdrawDao.update(withdraw);
		DepositWithdrawRequest.Builder builder = new DepositWithdrawRequest.Builder();
		builder.outTradeNo(withdraw.getId());
		builder.accountType(AccountType.valueOf(withdraw.getAccountType()));
		builder.identityId(withdraw.getWithdrawee());
		builder.amount(withdraw.getAmount());
		builder.userIp(param.meta().getIp());
		builder.withdrawCloseTime("1m");
		builder.extendParam("customNotify^Y"); // 支持申请成功通知
		DeviceType device = param.getUser().getDeviceType();
		if (device == DeviceType.MOBILE)
			builder.cashdeskAddrCategory(CashdeskAddrCategory.MOBILE);
		builder.returnUrl(_returnUrl(param.getUser()));
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_WITHDRAW_SINA));
		DepositWithdrawRequest request = builder.build();
		logger.info("新浪提现请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositWithdrawResponse response = request.sync();
		logger.info("新浪提现响应：{}", SerializeUtil.GSON.toJson(response));
		return response.getRedirectUrl();
	}
	
	@Transactional
	public SinaWithdraw withdrawNotice(WithdrawNotice notice) {
		Query query = new Query().eq("id", notice.getOuter_trade_no()).forUpdate();
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(query);
		Assert.notNull(SinaCode.WITHDRAW_NOT_EXIST, withdraw);
		SinaWithdrawState state = SinaWithdrawState.valueOf(withdraw.getState());
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, state == SinaWithdrawState.WITHDRAWING || state == SinaWithdrawState.PROCESSING);
		WithdrawState cstate = WithdrawState.valueOf(notice.getWithdraw_status());
		switch (cstate) {
		case SUCCESS:
			withdraw.setState(SinaWithdrawState.SUCCESS.name());
			withdraw.setUpdated(DateUtil.current());
			accountService.withdrawNotice(withdraw.getId(), true);
			break;
		case FAILED:
			withdraw.setState(SinaWithdrawState.FAILED.name());
			withdraw.setUpdated(DateUtil.current());
			break;
		case PROCESSING:
			withdraw.setState(SinaWithdrawState.PROCESSING.name());
			withdraw.setUpdated(DateUtil.current());
			break;
		default:
			throw new CodeException(SinaCode.UNRECOGNIZE_DATA);
		}
		sinaWithdrawDao.update(withdraw);
		return withdraw;
	}
	
	@Transactional
	public void withdrawCollect(String id, WithdrawNotice notice) {
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(new Query().eq("id", id).forUpdate());
		Assert.notNull(SinaCode.WITHDRAW_NOT_EXIST, withdraw);
		SinaWithdrawState state = SinaWithdrawState.valueOf(withdraw.getState());
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, state == SinaWithdrawState.FAILED);
		withdraw.setState(SinaWithdrawState.RECALLING.name());
		withdraw.setUpdated(DateUtil.current());
		sinaWithdrawDao.update(withdraw);
		SinaCollect collect = EntityGenerator.newSinaCollect(CollectType.WITHDRAW_FAILED, withdraw.getId());
		sinaCollectDao.insert(collect);
		DepositCollectRequest.Builder builder = new DepositCollectRequest.Builder();
		builder.payerId(withdraw.getWithdrawee());
		builder.payerIp(notice.meta().getIp());
		builder.summary("提现待收");
		builder.outTradeNo(collect.getId());
		builder.outTradeCode(OutTradeCode.COLLECT_INVEST);
		BalancePay balancePay = new BalancePay();
		balancePay.setAccountType(AccountType.SAVING_POT);
		builder.paymethod(balancePay, withdraw.getAmount());
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_COLLECT_SINA));
		DepositCollectRequest request = builder.build();
		logger.info("新浪提现待收请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪提现待收响应：{}", SerializeUtil.GSON.toJson(response));
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
			return RechargeState.WAIT_RECALL;
		case FAILED:
			return RechargeState.FAILED;
		default:
			throw new CodeException();
		}
	}
}
