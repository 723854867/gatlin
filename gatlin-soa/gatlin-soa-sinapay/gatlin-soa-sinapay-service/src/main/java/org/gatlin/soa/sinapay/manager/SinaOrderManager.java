package org.gatlin.soa.sinapay.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.Gatlin;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.BidType;
import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.DepositRechargeState;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.bean.enums.TradeState;
import org.gatlin.sdk.sinapay.bean.enums.WithdrawState;
import org.gatlin.sdk.sinapay.bean.model.BalancePay;
import org.gatlin.sdk.sinapay.bean.model.BindingCardPay;
import org.gatlin.sdk.sinapay.bean.model.BorrowerInfo;
import org.gatlin.sdk.sinapay.bean.model.OnlineBankPay;
import org.gatlin.sdk.sinapay.notice.BidNotice;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.sdk.sinapay.request.order.BidCreateRequest;
import org.gatlin.sdk.sinapay.request.order.DepositCollectRequest;
import org.gatlin.sdk.sinapay.request.order.DepositPayRequest;
import org.gatlin.sdk.sinapay.request.order.DepositRechargeRequest;
import org.gatlin.sdk.sinapay.request.order.DepositWithdrawRequest;
import org.gatlin.sdk.sinapay.request.order.PayToCardRequest;
import org.gatlin.sdk.sinapay.response.BidCreateResponse;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;
import org.gatlin.sdk.sinapay.response.DepositResponse;
import org.gatlin.sdk.sinapay.response.DepositWithdrawResponse;
import org.gatlin.sdk.sinapay.response.PayToCardResponse;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.sinapay.SinaBizHook;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.SinaConsts;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaLoanout;
import org.gatlin.soa.sinapay.bean.entity.SinaPay;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;
import org.gatlin.soa.sinapay.bean.enums.CollectType;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.enums.SinaWithdrawState;
import org.gatlin.soa.sinapay.bean.model.BidInfo;
import org.gatlin.soa.sinapay.bean.param.WithdrawParam;
import org.gatlin.soa.sinapay.mybatis.EntityGenerator;
import org.gatlin.soa.sinapay.mybatis.dao.SinaBidDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaCollectDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaLoanoutDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaPayDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaRechargeDao;
import org.gatlin.soa.sinapay.mybatis.dao.SinaWithdrawDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.bean.model.Pair;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SinaOrderManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SinaOrderManager.class);
	
	@Resource
	private Gatlin gatlin;
	@Resource
	private SinaPayDao sinaPayDao;
	@Resource
	private SinaBidDao sinaBidDao;
	@Autowired
	private SinaBizHook sinaBizHook;
	@Resource
	private ConfigService configService;
	@Resource
	private SinaLoanoutDao sinaLoanoutDao;
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
	public String depositRecharge(Recharge recharge, SoaParam param, String summary) {
		TargetType rechargerType = recharge.getRechargerType();
		AccountType accountType = rechargerType == TargetType.COMPANY ? AccountType.BASIC : AccountType.SAVING_POT;
		SinaUser recharger = sinaMemberManager.user(rechargerType == TargetType.COMPANY ? MemberType.ENTERPRISE : MemberType.PERSONAL, recharge.getRecharger());
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, recharger);
		Assert.isTrue(rechargerType == TargetType.USER ? SinaCode.USER_UNWITHHOLD : SinaCode.COMPANY_UNWITHHOLD, sinaMemberManager.isWithhold(recharger));
		SinaUser rechargee = null;
		if (recharge.getRechargeeType() == rechargerType && recharge.getRechargee() == recharge.getRecharger())
			rechargee = recharger;
		else
			rechargee = sinaMemberManager.user(recharge.getRechargeeType() == TargetType.COMPANY ? MemberType.ENTERPRISE : MemberType.PERSONAL, recharge.getRechargee());
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, rechargee);
		sinaBizHook.recharge(recharge);
		sinaRechargeDao.insert(EntityGenerator.newSinaRecharge(recharge, recharger.getSinaId(), rechargee.getSinaId(), summary));
		DepositRechargeRequest.Builder builder = new DepositRechargeRequest.Builder();
		builder.outTradeNo(recharge.getId());
		builder.accountType(accountType);
		OnlineBankPay pay = new OnlineBankPay();
		if (rechargerType == TargetType.COMPANY)			// 对公充值则卡属性为对公
			pay.setCardAttribute(CardAttribute.B);
		builder.summary(summary);
		builder.payMethod(pay, recharge.getAmount());
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
		logger.info("新浪{}请求：{}", summary, SerializeUtil.GSON.toJson(request.params()));
		DepositRechargeResponse response = request.sync();
		logger.info("新浪{}响应：{}", summary, SerializeUtil.GSON.toJson(response));
		return response.getRedirectUrl();
	}
	
	@Transactional
	public SinaRecharge depositRechargeTimeout(String id) {
		SinaRecharge recharge = sinaRechargeDao.queryUnique(new Query().eq("id", id).forUpdate());
		Assert.notNull(SinaCode.RECHARGE_NOT_EXIST, recharge);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, recharge.getState() == RechargeState.PROCESSING);
		recharge.setState(RechargeState.FAILED);
		recharge.setUpdated(DateUtil.current());
		sinaBizHook.rechargeNotice(id, org.gatlin.soa.account.bean.enums.RechargeState.TIMEOUT);
		sinaRechargeDao.update(recharge);
		return recharge;
	}
	
	@Transactional
	public SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice) {
		SinaRecharge recharge = sinaRechargeDao.queryUnique(new Query().eq("id", notice.getOuter_trade_no()).forUpdate());
		Assert.notNull(SinaCode.RECHARGE_NOT_EXIST, recharge);
		DepositRechargeState cstate = DepositRechargeState.valueOf(notice.getDeposit_status());
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, recharge.getState() == RechargeState.PROCESSING);
		Assert.isTrue(SinaCode.UNRECOGNIZE_DATA, cstate == DepositRechargeState.FAILED || cstate == DepositRechargeState.SUCCESS);
		recharge.setState(_rechargeState(cstate));
		recharge.setUpdated(DateUtil.current());
		if (cstate == DepositRechargeState.FAILED) 		// 充值失败同步通知应用充值失败
			sinaBizHook.rechargeNotice(recharge.getId(), org.gatlin.soa.account.bean.enums.RechargeState.CLOSE);
		sinaRechargeDao.update(recharge);
		return recharge;
	}
	
	/**
	 * 充值待收
	 */
	@Transactional
	public SinaCollect rechargeCollect(String id, String ip) {
		SinaRecharge recharge = sinaRechargeDao.queryUnique(new Query().eq("id", id).forUpdate());
		Assert.notNull(SinaCode.RECHARGE_NOT_EXIST, recharge);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, recharge.getState() == RechargeState.WAIT_RECALL);
		recharge.setState(RechargeState.RECALLING);
		recharge.setUpdated(DateUtil.current());
		sinaRechargeDao.update(recharge);
		String relativeNo = sinaBizHook.relativeIncome(recharge.getAmount());
		SinaCollect collect = EntityGenerator.newSinaCollect(CollectType.RECHARGE, recharge.getId());
		collect.setRelativeNo(relativeNo);
		sinaCollectDao.insert(collect);
		String summary = recharge.getSummary() + "待收";
		DepositCollectRequest.Builder builder = new DepositCollectRequest.Builder();
		builder.payerId(recharge.getRecharger());
		builder.payerIp(ip);
		builder.summary(summary);
		builder.outTradeNo(collect.getId());
		builder.tradeRelatedNo(collect.getRelativeNo());
		builder.outTradeCode(OutTradeCode.COLLECT_INVEST);
		BalancePay balancePay = new BalancePay();
		balancePay.setAccountType(recharge.getRechargerType() == TargetType.USER ? AccountType.SAVING_POT : AccountType.BASIC);
		builder.paymethod(balancePay, recharge.getAmount());
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_COLLECT_SINA));
		DepositCollectRequest request = builder.build();
		logger.info("新浪{}请求：{}", summary, SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪{}响应：{}", summary, SerializeUtil.GSON.toJson(response));
		return collect;
	}
	
	// 待收回调
	@Transactional
	public SinaCollect collectNotice(TradeNotice notice) {
		Query query = new Query().eq("id", notice.getOuter_trade_no()).forUpdate();
		SinaCollect collect = sinaCollectDao.queryUnique(query);
		Assert.notNull(SinaCode.RECHARGE_COLLECT_NOT_EXIST, collect);
		switch (collect.getState()) {
		case WAIT_PAY:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, notice.getTrade_status() == TradeState.PAY_FINISHED || notice.getTrade_status() == TradeState.TRADE_FAILED || notice.getTrade_status() == TradeState.TRADE_FINISHED || notice.getTrade_status() == TradeState.TRADE_CLOSED);
			break;
		case PAY_FINISHED:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, notice.getTrade_status() == TradeState.TRADE_FINISHED || notice.getTrade_status() == TradeState.TRADE_FAILED);
			break;
		default:
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		query = new Query().eq("id", collect.getTid()).forUpdate();
		switch (collect.getType()) {
		case RECHARGE:						// 充值待收
			SinaRecharge recharge = sinaRechargeDao.queryUnique(query);
			switch (notice.getTrade_status()) {
			case TRADE_FAILED:
			case TRADE_CLOSED:
				recharge.setState(RechargeState.WAIT_RECALL);
				recharge.setUpdated(DateUtil.current());
				sinaRechargeDao.update(recharge);
				break;
			case TRADE_FINISHED:
				recharge.setState(RechargeState.SUCCESS);
				recharge.setUpdated(DateUtil.current());
				sinaRechargeDao.update(recharge);
				sinaBizHook.rechargeNotice(recharge.getId(), org.gatlin.soa.account.bean.enums.RechargeState.SUCCESS);
				break;
			default:
				break;
			}
			break;
		case WITHDRAW_FAILED:						// 提现失败待收
			SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(query);
			switch (notice.getTrade_status()) {
			case TRADE_FAILED:
			case TRADE_CLOSED:
				withdraw.setState(SinaWithdrawState.FAILED);
				withdraw.setUpdated(DateUtil.current());
				sinaWithdrawDao.update(withdraw);
				break;
			case TRADE_FINISHED:
				withdraw.setState(SinaWithdrawState.RECALLED);
				withdraw.setUpdated(DateUtil.current());
				sinaWithdrawDao.update(withdraw);
				sinaBizHook.withdrawNotice(withdraw.getId(), false);
				break;
			default:
				break;
			}
			break;
		case WITHDRAW_TIMEOUT:
			withdraw = sinaWithdrawDao.queryUnique(query);
			switch (notice.getTrade_status()) {
			case TRADE_FAILED:
			case TRADE_CLOSED:
				withdraw.setState(SinaWithdrawState.PAYED);
				withdraw.setUpdated(DateUtil.current());
				sinaWithdrawDao.update(withdraw);
				break;
			case TRADE_FINISHED:
				withdraw.setState(SinaWithdrawState.RECALLED);
				withdraw.setUpdated(DateUtil.current());
				sinaWithdrawDao.update(withdraw);
				sinaBizHook.withdrawNotice(withdraw.getId(), false);
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
	public SinaWithdraw withdrawPay(WithdrawParam param) { 
		SinaUser user = sinaMemberManager.user(MemberType.PERSONAL, param.getUser().getId());
		Assert.isTrue(SinaCode.MEMBER_NOT_EXIST, null != user);
		Withdraw withdraw = sinaBizHook.withdraw(param);
		SinaWithdraw sinaWithdraw = EntityGenerator.newSinaWithdraw(withdraw, user, AccountType.SAVING_POT);
		sinaWithdrawDao.insert(sinaWithdraw);
		return sinaWithdraw;
	}
	
	@Transactional
	public BigDecimal withdrawPay(SinaWithdraw withdraw, BigDecimal amount) {
		Pair<String, BigDecimal> pair = sinaBizHook.relativeOutcome(amount);
		SinaPay pay = EntityGenerator.newSinaPay(withdraw);
		pay.setRelativeNo(pair.getKey());
		pay.setAmount(pair.getValue());
		sinaPayDao.insert(pay);
		DepositPayRequest.Builder builder = new DepositPayRequest.Builder();
		builder.outTradeNo(pay.getId());
		builder.accountType(AccountType.SAVING_POT);
		builder.payeeIdentityId(withdraw.getWithdrawee());
		builder.amount(pay.getAmount());
		builder.summary("个人提现代付");
		builder.tradeRelatedNo(pay.getRelativeNo());
		builder.userIp("127.0.0.1");
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_WITHDRAW_PAY_SINA));
		DepositPayRequest request = builder.build();
		logger.info("新浪提个人现代付请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪提个人现代付响应：{}", SerializeUtil.GSON.toJson(response));
		return pair.getValue();
	}
	
	@Transactional
	public void withdrawPayNotice(TradeNotice notice) { 
		Query query = new Query().eq("id", notice.getOuter_trade_no()).forUpdate();
		SinaPay pay = sinaPayDao.queryUnique(query);
		Assert.notNull(SinaCode.WITHDRAW_PAY_NOT_EXIST, pay);
		switch (pay.getState()) {
		case WAIT_PAY:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, notice.getTrade_status() == TradeState.PAY_FINISHED || notice.getTrade_status() == TradeState.TRADE_FAILED || notice.getTrade_status() == TradeState.TRADE_FINISHED || notice.getTrade_status() == TradeState.TRADE_CLOSED);
			break;
		case PAY_FINISHED:
			Assert.isTrue(CoreCode.DATA_STATE_CHANGED, notice.getTrade_status() == TradeState.TRADE_FINISHED || notice.getTrade_status() == TradeState.TRADE_FAILED);
			break;
		default:
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		pay.setState(notice.getTrade_status());
		pay.setUpdated(DateUtil.current());
		sinaPayDao.update(pay);
		switch (pay.getState()) {
		case TRADE_FAILED:
		case TRADE_CLOSED:
			sinaBizHook.relativeOutcomeNotice(pay.getRelativeNo(), pay.getAmount(), false);
			break;
		case TRADE_FINISHED:
			sinaBizHook.relativeOutcomeNotice(pay.getRelativeNo(), pay.getAmount(), true);
			break;
		default:
			break;
		}
		
		List<SinaPay> pays = sinaPayDao.queryList(new Query().eq("withdraw_id", pay.getWithdrawId()).forUpdate());
		int process = 0;
		int success = 0;
		BigDecimal total = BigDecimal.ZERO;
		for (SinaPay temp : pays) {
			switch (temp.getState()) {
			case TRADE_FAILED:
			case TRADE_CLOSED:
				break;
			case TRADE_FINISHED:
				success++;
				break;
			default:
				process++;
				break;
			}
			total = total.add(temp.getAmount());
		}
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(new Query().eq("id", pay.getWithdrawId()).forUpdate());
		if (withdraw.getAmount().compareTo(total) != 0)		// 代付和提现的金额不等直接返回等待超时即可
			return;
		if (process == 0) {		// 代付已经全部处理好
			if (success == pays.size()) // 全部成功
				withdraw.setState(SinaWithdrawState.PAYED);
			else 		// 一个失败全部失败
				withdraw.setState(SinaWithdrawState.PAY_FAILED);
			withdraw.setUpdated(DateUtil.current());
			sinaWithdrawDao.update(withdraw);
		}
	}
	
	@Transactional
	public String withdraw(SoaSidParam param) {
		Query query = new Query().eq("id", param.getId()).forUpdate();
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(query);
		Assert.notNull(SinaCode.WITHDRAW_NOT_EXIST, withdraw);
		if (withdraw.getState() != SinaWithdrawState.PAYED) {
			if (withdraw.getState() == SinaWithdrawState.WAIT_PAY)
				throw new CodeException(SinaCode.WITHDRAW_UNPAYED);
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		withdraw.setState(SinaWithdrawState.WITHDRAWING);
		withdraw.setUpdated(DateUtil.current());
		sinaWithdrawDao.update(withdraw);
		DepositWithdrawRequest.Builder builder = new DepositWithdrawRequest.Builder();
		builder.outTradeNo(withdraw.getId());
		builder.accountType(withdraw.getAccountType());
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
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, withdraw.getState() == SinaWithdrawState.WITHDRAWING || withdraw.getState() == SinaWithdrawState.PROCESSING);
		switch (notice.getWithdraw_status()) {
		case SUCCESS:
			withdraw.setState(SinaWithdrawState.SUCCESS);
			withdraw.setUpdated(DateUtil.current());
			sinaBizHook.withdrawNotice(withdraw.getId(), true);
			break;
		case FAILED:
			withdraw.setState(SinaWithdrawState.FAILED);
			withdraw.setUpdated(DateUtil.current());
			break;
		case PROCESSING:
			withdraw.setState(SinaWithdrawState.PROCESSING);
			withdraw.setUpdated(DateUtil.current());
			break;
		default:
			throw new CodeException(SinaCode.UNRECOGNIZE_DATA);
		}
		sinaWithdrawDao.update(withdraw);
		return withdraw;
	}
	
	@Transactional
	public void withdrawTimeout(String id) {
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(new Query().eq("id", id).forUpdate());
		Assert.notNull(SinaCode.WITHDRAW_NOT_EXIST, withdraw);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, withdraw.getState() == SinaWithdrawState.PAY_FAILED 
				|| withdraw.getState() == SinaWithdrawState.PAYED || withdraw.getState() == SinaWithdrawState.WAIT_PAY);
		_withdrawCollect(withdraw, CollectType.WITHDRAW_TIMEOUT, "127.0.0.1");
	}
	
	@Transactional
	public void withdrawFailure(String id, WithdrawNotice notice) {
		SinaWithdraw withdraw = sinaWithdrawDao.queryUnique(new Query().eq("id", id).forUpdate());
		Assert.notNull(SinaCode.WITHDRAW_NOT_EXIST, withdraw);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, withdraw.getState() == SinaWithdrawState.FAILED);
		_withdrawCollect(withdraw, CollectType.WITHDRAW_FAILED, notice.meta().getIp());
	}
	
	private void _withdrawCollect(SinaWithdraw withdraw, CollectType collectType, String ip) {
		List<SinaPay> pays = sinaPayDao.queryList(new Query().eq("withdraw_id", withdraw.getId()).forUpdate());
		BigDecimal amount = BigDecimal.ZERO;
		for (SinaPay temp : pays) {
			if (temp.getState() == TradeState.TRADE_FINISHED) {
				amount = amount.add(temp.getAmount());
			}
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
			return; 
		withdraw.setState(SinaWithdrawState.RECALLING);
		withdraw.setUpdated(DateUtil.current());
		sinaWithdrawDao.update(withdraw);
		String relativeNo = sinaBizHook.relativeIncome(amount);
		SinaCollect collect = EntityGenerator.newSinaCollect(collectType, withdraw.getId());
		collect.setRelativeNo(relativeNo);
		sinaCollectDao.insert(collect);
		DepositCollectRequest.Builder builder = new DepositCollectRequest.Builder();
		builder.payerId(withdraw.getWithdrawee());
		builder.payerIp(ip);
		builder.summary(collectType.describe());
		builder.outTradeNo(collect.getId());
		builder.outTradeCode(OutTradeCode.COLLECT_INVEST);
		builder.tradeRelatedNo(relativeNo);
		BalancePay balancePay = new BalancePay();
		balancePay.setAccountType(AccountType.SAVING_POT);
		builder.paymethod(balancePay, amount);
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_COLLECT_SINA));
		DepositCollectRequest request = builder.build();
		logger.info("新浪{}请求：{}", collectType.describe(), SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪{}响应：{}", collectType.describe(), SerializeUtil.GSON.toJson(response));
	}
	
	@Transactional
	public void bidCreate(BidInfo info) { 
		MemberType type = info.getBtype() == TargetType.USER ? MemberType.PERSONAL : MemberType.ENTERPRISE;
		SinaUser user = sinaMemberManager.user(type, info.getBorrower());
		SinaUser company = type == MemberType.ENTERPRISE ? user : sinaMemberManager.user(MemberType.ENTERPRISE, info.getCompanyId());
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user, company);
		SinaBid bid = EntityGenerator.newSinaBid(user, company, info);
		sinaBidDao.insert(bid);
		BidCreateRequest.Builder builder = new BidCreateRequest.Builder();
		builder.outBidNo(bid.getId());
		builder.webSiteName("微钱进");
		builder.bidName(StringUtil.uuid());
		builder.bidType(BidType.CREDIT);
		builder.bidAmount(info.getAmount());
		builder.bidYearRate(info.getRate().multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP));
		builder.bidDuration(info.getDuration());
		builder.repayType(info.getRepayType());
		builder.beginDate(info.getBeginDate());
		builder.term(info.getExpiryDate());
		builder.guaranteeMethod("企业担保");
		List<BorrowerInfo> borrowers = new ArrayList<BorrowerInfo>();
		borrowers.add(new BorrowerInfo(user.getSinaId(), String.valueOf(PhoneUtil.getNationalNumber(info.getMobile())), "车贷", info.getAmount()));
		builder.borrowerInfoList(borrowers);
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_BID_SINA));
		BidCreateRequest request = builder.build();
		logger.info("新浪标的录入请求：{}", SerializeUtil.GSON.toJson(request.params()));
		BidCreateResponse response = request.sync();
		logger.info("新浪标的录入响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	@Transactional
	public SinaBid bidNotice(BidNotice notice) { 
		Query query = new Query().eq("id", notice.getOut_bid_no()).forUpdate();
		SinaBid bid = sinaBidDao.queryUnique(query);
		Assert.notNull(SinaCode.BID_NOT_EXIST, bid);
		switch (bid.getState()) {
		case INIT:
		case AUDITING:
			bid.setState(notice.getBid_status());
			bid.setUpdated(DateUtil.current());
			break;
		default:
			throw new CodeException(CoreCode.DATA_STATE_CHANGED);
		}
		sinaBidDao.update(bid);
		sinaBizHook.bidNotice(bid, notice);
		return bid;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void loanout(SinaLoanout total, SinaBankCard card) {
		Pair<String, BigDecimal> pair = sinaBizHook.relativeOutcome(total.getAmount());
		SinaLoanout loanout = EntityGenerator.newSinaLoanout(total, pair.getValue());
		loanout.setRelativeNo(pair.getKey());
		sinaLoanoutDao.insert(loanout);
		PayToCardRequest.Builder builder = new PayToCardRequest.Builder();
		builder.outTradeNo(loanout.getId());
		BindingCardPay pay = new BindingCardPay();
		pay.setCardId(card.getSinaCardId());
		pay.setSinaId(loanout.getMemberId());
		builder.collectMethod(pay);
		builder.amount(loanout.getAmount());
		builder.summary(loanout.getDesc());
		builder.tradeRelatedNo(loanout.getRelativeNo());
		builder.goodsId(loanout.getBidId());
		builder.notifyUrl(configService.config(SinaConsts.URL_NOTICE_LOANOUT));
		builder.userIp(loanout.getIp());
		PayToCardRequest request = builder.build();
		logger.info("新浪放款请求：{}", SerializeUtil.GSON.toJson(request.params()));
		PayToCardResponse response = request.sync();
		logger.info("新浪放款响应：{}", SerializeUtil.GSON.toJson(response));
		total.setAmount(total.getAmount().subtract(pair.getValue()));
	}
	
	@Transactional
	public void loanoutNotice(WithdrawNotice notice) {
		Query query = new Query().eq("id", notice.getOuter_trade_no()).forUpdate();
		SinaLoanout loanout = sinaLoanoutDao.queryUnique(query);
		Assert.notNull(SinaCode.LOANOUT_NOT_EXIST, loanout);
		Assert.isTrue(CoreCode.DATA_STATE_CHANGED, loanout.getState() == WithdrawState.INIT || loanout.getState() == WithdrawState.PROCESSING);
		switch (notice.getWithdraw_status()) {
		case SUCCESS:
			loanout.setState(WithdrawState.SUCCESS);
			loanout.setUpdated(DateUtil.current());
			sinaBizHook.relativeOutcomeNotice(loanout.getRelativeNo(), loanout.getAmount(), true);
			break;
		case FAILED:
			loanout.setState(WithdrawState.FAILED);
			loanout.setUpdated(DateUtil.current());
			sinaBizHook.relativeOutcomeNotice(loanout.getRelativeNo(), loanout.getAmount(), false);
			break;
		case PROCESSING:
			loanout.setState(WithdrawState.PROCESSING);
			loanout.setUpdated(DateUtil.current());
			break;
		default:
			throw new CodeException(SinaCode.UNRECOGNIZE_DATA);
		}
		sinaLoanoutDao.update(loanout);
		if (loanout.getState() == WithdrawState.PROCESSING)
			return;
		SinaBid bid = sinaBidDao.getByKey(loanout.getBidId());
		List<SinaLoanout> loanouts = sinaLoanoutDao.queryList(new Query().eq("biz_id", bid.getBizId()).eq("purpose", bid.getPurpose()).forUpdate());
		int process = 0;
		int success = 0;
		BigDecimal total = BigDecimal.ZERO;
		for (SinaLoanout temp : loanouts) {
			switch (temp.getState()) {
			case FAILED:
			case RETURNT_TICKET:
				break;
			case SUCCESS:
				success++;
			default:
				process++;
				break;
			}
			total = total.add(temp.getAmount());
		}
		if (total.compareTo(bid.getAmount()) != 0)			
			return;
		if (process == 0) {
			if (success == loanouts.size())
				sinaBizHook.loanoutNotice(bid, loanout.getCompanyId(), true);
		}
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
	
	public SinaBid bid(BidPurpose purpose, String bizId) {
		return sinaBidDao.queryUnique(new Query().eq("purpose", purpose).eq("biz_id", bizId).forUpdate());
	}
	
	@Transactional
	public void pay(SinaUser user, BigDecimal amount) {
		SinaPay pay = EntityGenerator.newSinaPay();
		pay.setWithdrawId(StringUtil.EMPTY);
		pay.setRelativeNo(StringUtil.EMPTY);
		pay.setAmount(amount);
		sinaPayDao.insert(pay);
		DepositPayRequest.Builder builder = new DepositPayRequest.Builder();
		builder.accountType(AccountType.SAVING_POT);
		builder.payeeIdentityId(user.getSinaId());
		builder.amount(pay.getAmount());
		builder.summary("清空中间账户代付");
		builder.tradeRelatedNo(pay.getRelativeNo());
		builder.userIp("127.0.0.1");
		builder.notifyUrl("http://183.230.148.236/test");
		DepositPayRequest request = builder.build();
		logger.info("新浪清空中间账户代付请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪清空中间账户代付响应：{}", SerializeUtil.GSON.toJson(response));
	}
	
	@Transactional
	public void collect(SinaUser user, String relativeNo, BigDecimal amount) {
		DepositCollectRequest.Builder builder = new DepositCollectRequest.Builder();
		builder.payerId(user.getSinaId());
		builder.payerIp("127.0.0.1");
		builder.summary("新浪清空中间账户待收");
		builder.outTradeNo(IDWorker.INSTANCE.nextSid());
		builder.tradeRelatedNo(relativeNo);
		builder.outTradeCode(OutTradeCode.COLLECT_INVEST);
		BalancePay balancePay = new BalancePay();
		balancePay.setAccountType(AccountType.SAVING_POT);
		builder.paymethod(balancePay, amount);
		builder.notifyUrl("http://183.230.148.236/test");
		DepositCollectRequest request = builder.build();
		logger.info("新浪新浪清空中间账户待收请求：{}", SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		logger.info("新浪新浪清空中间账户待收响应：{}", SerializeUtil.GSON.toJson(response));
	}
}
