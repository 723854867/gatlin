package org.gatlin.soa.sinapay.mybatis;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.CompanyAuditState;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.enums.TradeState;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.entity.Withdraw;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaCompanyAudit;
import org.gatlin.soa.sinapay.bean.entity.SinaPay;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.CollectType;
import org.gatlin.soa.sinapay.bean.enums.RechargeState;
import org.gatlin.soa.sinapay.bean.enums.SinaWithdrawState;
import org.gatlin.soa.sinapay.bean.param.CompanyApplyParam;
import org.gatlin.soa.sinapay.bean.param.RechargeParam;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final SinaUser newSinaUser(String tid, MemberType type) {
		SinaUser instance = new SinaUser();
		instance.setTid(tid);
		instance.setType(type.mark());
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
		instance.setSinaUid(user.getSinaId());
		instance.setProvince(geo.getProvince());
		instance.setUid(Long.valueOf(user.getTid()));
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final BankCard newBankCard(SinaBankCard cardBind) {
		BankCard instance = new BankCard();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setOwner(cardBind.getUid());
		instance.setBankId(cardBind.getBankId());
		instance.setOwnerType(TargetType.USER.mark());
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
	
	public static final SinaRecharge newSinaRecharge(Recharge recharge, RechargeParam param, TargetType rechargerType, String recharger, 
			TargetType rechargeeType, String rechargee) {
		SinaRecharge instance = new SinaRecharge();
		instance.setId(recharge.getId());
		instance.setRechargee(rechargee);
		instance.setRecharger(recharger);
		instance.setRechargerType(rechargerType.name());
		instance.setRechargeeType(rechargeeType.name());
		instance.setAmount(recharge.getAmount());
		instance.setState(RechargeState.PROCESSING.name());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaCollect newSinaCollect(CollectType type, String tid) {
		SinaCollect instance = new SinaCollect();
		instance.setState(TradeState.WAIT_PAY.name());
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setTid(tid);
		instance.setType(type.name());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaPay newSinaPay(Withdraw withdraw) {
		SinaPay instance = new SinaPay();
		instance.setWithdrawId(withdraw.getId());
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setState(TradeState.WAIT_PAY.name());
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
		instance.setAccountType(accountType.name());
		instance.setState(SinaWithdrawState.WAIT_PAY.name());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final SinaCompanyAudit newSinaCompanyAudit(CompanyApplyParam param, Geo geo) {
		SinaCompanyAudit instance = new SinaCompanyAudit();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setCid(param.getId());
		instance.setState(CompanyAuditState.PROCESSING.name());
		instance.setCity(geo.getCity());
		instance.setProvince(geo.getProvince());
		instance.setMobile(param.getMobile());
		instance.setBranch(param.getBranch());
		instance.setBankId(param.getBankId());
		instance.setBankNo(param.getBankNo());
		instance.setAuditMsg(StringUtil.EMPTY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final BankCard newBankCard(SinaCompanyAudit companyAudit) {
		BankCard instance = new BankCard();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setOwner(companyAudit.getCid());
		instance.setBankId(companyAudit.getBankId());
		instance.setOwnerType(TargetType.COMPANY.mark());
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
}
