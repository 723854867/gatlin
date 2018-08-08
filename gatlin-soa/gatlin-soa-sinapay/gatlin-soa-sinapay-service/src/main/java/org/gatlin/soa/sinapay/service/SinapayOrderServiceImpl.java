package org.gatlin.soa.sinapay.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.notice.BidNotice;
import org.gatlin.sdk.sinapay.notice.DepositRechargeNotice;
import org.gatlin.sdk.sinapay.notice.TradeNotice;
import org.gatlin.sdk.sinapay.notice.WithdrawNotice;
import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.sinapay.api.SinapayOrderService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaBid;
import org.gatlin.soa.sinapay.bean.entity.SinaCollect;
import org.gatlin.soa.sinapay.bean.entity.SinaLoanout;
import org.gatlin.soa.sinapay.bean.entity.SinaRecharge;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.entity.SinaWithdraw;
import org.gatlin.soa.sinapay.bean.enums.BankCardState;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;
import org.gatlin.soa.sinapay.bean.model.BidInfo;
import org.gatlin.soa.sinapay.bean.param.WithdrawParam;
import org.gatlin.soa.sinapay.manager.SinaMemberManager;
import org.gatlin.soa.sinapay.manager.SinaOrderManager;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service("sinapayOrderService")
public class SinapayOrderServiceImpl implements SinapayOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(SinapayOrderServiceImpl.class);
	
	@Resource
	private SinaOrderManager sinaOrderManager;
	@Resource
	private SinaMemberManager sinaMemberManager;

	@Override
	public String depositRecharge(Recharge recharge, SoaParam param, String summary) {
		return sinaOrderManager.depositRecharge(recharge, param, summary);
	}
	
	@Override
	public void depositRechargeTimeout(String id) {
		sinaOrderManager.depositRechargeTimeout(id);
	}
	
	@Override
	public SinaRecharge noticeDepositRecharge(DepositRechargeNotice notice) {
		return sinaOrderManager.noticeDepositRecharge(notice);
	}
	
	@Override
	public SinaCollect rechargeCollect(String id, String ip) {
		return sinaOrderManager.rechargeCollect(id, ip);
	}
	
	@Override
	public SinaCollect collectNotice(TradeNotice notice) {
		return sinaOrderManager.collectNotice(notice);
	}

	@Override
	public String withdrawPay(WithdrawParam param) {
		SinaWithdraw withdraw = sinaOrderManager.withdrawPay(param);
		BigDecimal amount = withdraw.getAmount();
		while (amount.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal payed = sinaOrderManager.withdrawPay(withdraw, amount);
			amount = amount.subtract(payed);
		}
		return withdraw.getId();
	}
	
	@Override
	public void withdrawPayNotice(TradeNotice notice) {
		sinaOrderManager.withdrawPayNotice(notice);
	}
	
	@Override
	public String withdraw(SoaSidParam param) {
		return sinaOrderManager.withdraw(param);
	}
	
	@Override
	public void withdrawTimeout(String id) {
		sinaOrderManager.withdrawTimeout(id);
	}
	
	@Override
	public SinaWithdraw withdrawNotice(WithdrawNotice notice) {
		return sinaOrderManager.withdrawNotice(notice);
	}
	
	@Override
	public void withdrawFailure(String id, WithdrawNotice notice) {
		sinaOrderManager.withdrawFailure(id, notice);
	}
	
	@Override
	public SinaBid bid(BidPurpose purpose, String bizId) {
		return sinaOrderManager.bid(purpose, bizId);
	}
	
	@Override
	public void bidCreate(BidInfo info) {
		sinaOrderManager.bidCreate(info);
	}
	
	@Override
	public SinaBid bidNotice(BidNotice notice) {
		return sinaOrderManager.bidNotice(notice);
	}
	
	@Override
	public void loanout(SinaLoanout loanout) {
		Query query = new Query().eq("owner", loanout.getMemberId()).eq("state", BankCardState.BINDED.name());
		List<SinaBankCard> cards = sinaMemberManager.bankCards(query);
		Iterator<SinaBankCard> iterator = cards.iterator();
		SinaBankCard card = null;
		while (iterator.hasNext()) {
			SinaBankCard bankCard = iterator.next();
			if (StringUtil.hasText(bankCard.getSinaCardId())) {
				card = bankCard;
				break;
			}
		}
		Assert.notNull(SinaCode.BANK_CARD_ID_NOT_RESET, card);
		while (loanout.getAmount().compareTo(BigDecimal.ZERO) > 0) {
			try {
				sinaOrderManager.loanout(loanout, card);
			} catch (Exception e) {
				logger.warn("标的放款失败 {}!", SerializeUtil.GSON.toJson(loanout));
				break;
			}
		}
	}
	
	@Override
	public void loanoutNotice(WithdrawNotice notice) {
		sinaOrderManager.loanoutNotice(notice);
	}
	
	@Override
	public void pay(long uid, BigDecimal amount) {
		SinaUser user = sinaMemberManager.user(MemberType.PERSONAL, uid);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		sinaOrderManager.pay(user, amount);
	}
	
	@Override
	public void collect(String id, long uid, BigDecimal amount) {
		SinaUser user = sinaMemberManager.user(MemberType.PERSONAL, uid);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		sinaOrderManager.collect(user, id, amount);
	}
}
