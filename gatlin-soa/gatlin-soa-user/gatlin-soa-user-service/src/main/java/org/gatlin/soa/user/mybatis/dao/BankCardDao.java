package org.gatlin.soa.user.mybatis.dao;

import java.util.List;

import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.user.bean.entity.BankCard;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;

public interface BankCardDao extends DBDao<String, BankCard> {

	List<BankCardInfo> list(BankCardsParam param);
}
