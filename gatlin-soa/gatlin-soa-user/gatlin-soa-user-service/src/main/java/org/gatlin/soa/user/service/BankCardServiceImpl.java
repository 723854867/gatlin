package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;
import org.gatlin.soa.user.manager.BankCardManager;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("bankCardService")
public class BankCardServiceImpl implements BankCardService {
	
	@Resource
	private BankCardManager bankCardManager;
	
	@Override
	public BankCard card(String id) {
		return bankCardManager.card(id);
	}
	
	@Override
	public void cardBind(BankCard card) {
		bankCardManager.cardBind(card);
	}
	
	@Override
	public void cardUnbind(String cardId) {
		bankCardManager.cardUnbind(cardId);
	}

	@Override
	public Pager<BankCardInfo> cards(BankCardsParam param) {
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return new Pager<BankCardInfo>(bankCardManager.cards(param));
	}
}
