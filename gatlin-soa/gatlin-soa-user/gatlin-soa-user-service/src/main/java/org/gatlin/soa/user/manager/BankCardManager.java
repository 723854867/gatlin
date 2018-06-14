package org.gatlin.soa.user.manager;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;
import org.gatlin.soa.user.mybatis.dao.BankCardDao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class BankCardManager {

	@Resource
	private BankCardDao bankCardDao;
	
	public void cardBind(BankCard card) { 
		try {
			bankCardDao.insert(card);
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.BANK_CARD_ALREADY_BIND);
		}
	}
	
	public void cardUnbind(String cardId) { 
		bankCardDao.deleteByKey(cardId);
	}
	
	public BankCard card(String key) {
		return bankCardDao.getByKey(key);
	}
	
	public List<BankCardInfo> cards(BankCardsParam param) {
		return bankCardDao.list(param);
	}
}
