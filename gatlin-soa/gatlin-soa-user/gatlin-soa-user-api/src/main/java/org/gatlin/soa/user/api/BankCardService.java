package org.gatlin.soa.user.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;

public interface BankCardService {

	BankCard card(String id);
	
	void cardBind(BankCard card);
	
	void cardUnbind(String cardId);
	
	Pager<BankCardInfo> cards(BankCardsParam param);
}
