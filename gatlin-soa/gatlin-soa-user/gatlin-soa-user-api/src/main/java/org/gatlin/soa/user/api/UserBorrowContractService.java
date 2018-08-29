package org.gatlin.soa.user.api;

import java.math.BigDecimal;
import java.util.List;

import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.user.bean.entity.UserBorrowContract;

public interface UserBorrowContractService {

	UserBorrowContract selectOneForList(BigDecimal money);

	UserBorrowContract selectOneForAssetList();

	UserBorrowContract selectOneForListByBorrowName(BigDecimal moeny, String name);

	long insert(UserBorrowContract userBorrowContract);

	void update(UserBorrowContract userBorrowContract);

	List<UserBorrowContract> getByInvestId(String InvestId);

	List<UserBorrowContract> selectContractBycontract();

	void batchInsert(List<UserBorrowContract> list);

	UserBorrowContract getById(long id);

	List<UserBorrowContract> getByInvestIdList(String InvestId);

	int batchUpdate(List<UserBorrowContract> list);

	List<UserBorrowContract> queryList(Query query);
}
