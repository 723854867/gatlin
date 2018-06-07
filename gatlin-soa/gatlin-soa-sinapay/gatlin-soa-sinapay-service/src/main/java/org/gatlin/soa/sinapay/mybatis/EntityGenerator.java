package org.gatlin.soa.sinapay.mybatis;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.BidState;
import org.gatlin.sdk.sinapay.bean.enums.CompanyAuditState;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.enums.TradeState;
import org.gatlin.sdk.sinapay.bean.enums.WithdrawState;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaCompanyAudit;
import org.gatlin.soa.sinapay.bean.entity.SinaLoanout;
import org.gatlin.soa.sinapay.bean.entity.SinaPay;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.BankCardState;
import org.gatlin.soa.sinapay.bean.enums.CollectType;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.enums.SinaWithdrawState;
import org.gatlin.soa.sinapay.bean.model.BidInfo;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final SinaUser newSinaUser(String tid, MemberType type) {
		SinaUser instance = new SinaUser();
		instance.setTid(tid);
		instance.setType(type);
		instance.setSinaId(IDWorker.INSTANCE.nextSid());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaBankCard newSinaBankCard(SinaUser user, String requestNo, BankCardBindParam param, Geo geo, SinaBank bank, String ticket) {
		SinaBankCard instance = new SinaBankCard();
		instance.setId(requestNo);
		instance.setTicket(ticket);
		instance.setCity(geo.getCity());
		instance.setIp(param.meta().getIp());
		instance.setBankId(bank.getId());
		instance.setBankNo(param.getBankNo());
		instance.setBranch(param.getBranch());
		instance.setMobile(param.getMobile());
		instance.setOwner(user.getSinaId());
		instance.setState(BankCardState.BINDING);
		instance.setProvince(geo.getProvince());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaBankCard newSinaBankCard(BankCard card, SinaCompanyAudit companyAudit) {
		SinaBankCard instance = new SinaBankCard();
		instance.setId(companyAudit.getId());
		instance.setCity(card.getCity());
		instance.setIp(companyAudit.getIp());
		instance.setBankId(card.getBankId());
		instance.setBankNo(card.getNo());
		instance.setBranch(card.getBranch());
		instance.setMobile(card.getMobile());
		instance.setProvince(card.getProvince());
		instance.setOwner(companyAudit.getSinaUid());
		instance.setCardId(card.getId());
		instance.setState(BankCardState.BINDED);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final BankCard newBankCard(SinaBankCard cardBind, SinaUser user) {
		BankCard instance = new BankCard();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setOwner(Long.valueOf(user.getTid()));
		instance.setBankId(cardBind.getBankId());
		instance.setOwnerType(TargetType.USER);
		instance.setNo(cardBind.getBankNo());
		instance.setMobile(cardBind.getMobile());
		instance.setProvince(cardBind.getProvince());
		instance.setCity(cardBind.getCity());
		instance.setBranch(cardBind.getBranch());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaRecharge newSinaRecharge(Recharge recharge, String recharger, String rechargee) {
		SinaRecharge instance = new SinaRecharge();
		instance.setId(recharge.getId());
		instance.setRechargee(rechargee);
		instance.setRecharger(recharger);
		instance.setRechargerType(recharge.getRechargerType());
		instance.setRechargeeType(recharge.getRechargeeType());
		instance.setAmount(recharge.getAmount());
		instance.setState(RechargeState.PROCESSING.name());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaCollect newSinaCollect(CollectType type, String tid) {
		SinaCollect instance = new SinaCollect();
		instance.setState(TradeState.WAIT_PAY);
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setTid(tid);
		instance.setType(type);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaPay newSinaPay(Withdraw withdraw) {
		SinaPay instance = new SinaPay();
		instance.setWithdrawId(withdraw.getId());
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setState(TradeState.WAIT_PAY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaWithdraw newSinaWithdraw(Withdraw withdraw, SinaUser user, AccountType accountType) {
		SinaWithdraw instance = new SinaWithdraw();
		instance.setId(withdraw.getId());
		instance.setWithdrawee(user.getSinaId());
		instance.setAmount(withdraw.getAmount());
		instance.setAccountType(accountType);
		instance.setState(SinaWithdrawState.WAIT_PAY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaCompanyAudit newSinaCompanyAudit(SinaUser user, CompanyApplyParam param, Geo geo) {
		SinaCompanyAudit instance = new SinaCompanyAudit();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setState(CompanyAuditState.PROCESSING);
		instance.setCity(geo.getCity());
		instance.setIp(param.meta().getIp());
		instance.setProvince(geo.getProvince());
		instance.setMobile(param.getMobile());
		instance.setBranch(param.getBranch());
		instance.setBankId(param.getBankId());
		instance.setBankNo(param.getBankNo());
		instance.setSinaUid(user.getSinaId());
		instance.setAuditMsg(StringUtil.EMPTY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final BankCard newBankCard(SinaCompanyAudit companyAudit, SinaUser user) {
		BankCard instance = new BankCard();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setOwner(Long.valueOf(user.getTid()));
		instance.setBankId(companyAudit.getBankId());
		instance.setOwnerType(TargetType.COMPANY);
		instance.setNo(companyAudit.getBankNo());
		instance.setMobile(companyAudit.getMobile());
		instance.setProvince(companyAudit.getProvince());
		instance.setCity(companyAudit.getCity());
		instance.setBranch(companyAudit.getBranch());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaBid newSinaBid(SinaUser user, BidInfo info) {
		SinaBid instance = new SinaBid();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setPurpose(info.getPurpose());
		instance.setBizId(info.getBizId());
		instance.setBorrower(user.getSinaId());
		instance.setState(BidState.INIT);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaLoanout newSinaLoanout(SinaBid bid, BigDecimal amount) {
		SinaLoanout instance = new SinaLoanout();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setBidId(bid.getId());
		instance.setAmount(amount);
		instance.setState(WithdrawState.INIT);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
