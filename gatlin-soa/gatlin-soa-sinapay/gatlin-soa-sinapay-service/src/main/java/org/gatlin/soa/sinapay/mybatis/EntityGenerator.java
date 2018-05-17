package org.gatlin.soa.sinapay.mybatis;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.bean.entity.SinaCardBind;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.enums.CardOwnerType;
import org.gatlin.soa.user.bean.param.BankCardBindParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

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
	
	public static final SinaCardBind newSinaCardBind(SinaUser user, String requestNo, BankCardBindParam param, Geo geo, SinaBank bank, String ticket) {
		SinaCardBind instance = new SinaCardBind();
		instance.setId(requestNo);
		instance.setTicket(ticket);
		instance.setCity(geo.getCity());
		instance.setIp(param.meta().getIp());
		instance.setBankId(bank.getSinaId());
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
	
	public static final BankCard newBankCard(SinaCardBind cardBind) {
		BankCard instance = new BankCard();
		instance.setId(IDWorker.INSTANCE.nextSid());
		instance.setOwner(cardBind.getUid());
		instance.setOwnerType(CardOwnerType.USER.mark());
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
}
