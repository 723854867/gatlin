package org.gatlin.soa.user.mybatis.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.user.bean.entity.UserBorrowContract;

public interface UserBorrowContractDao extends DBDao<Long, UserBorrowContract> {
	@Select("select a.borrow_name as borrow_name,sa.bidentity as borrow_identity,sa.bmobile as borrow_mobile from (select borrow_name,sum(amount) as amount  from user_borrow_contract  GROUP BY borrow_name ) as a,asset as sa where   a.borrow_name =sa.bname and   a.amount+#{moeny}<200000 ORDER BY  RAND() LIMIT 1")
	UserBorrowContract selectOneForList(BigDecimal moeny);

	@Select("select a.bname as borrow_name,a.bidentity as borrow_identity,a.bmobile as borrow_mobile from asset as a  ORDER BY RAND() LIMIT 1")
	UserBorrowContract selectOneForAsset();
	
	
	@Select("select a.bname as borrow_name,a.bidentity as borrow_identity,a.bmobile as borrow_mobile from asset as a ,user_borrow_contract as u where a.bname != u.borrow_name  ORDER BY RAND() LIMIT 1")
	UserBorrowContract selectOneForAssetToBorrowContract();
	
	
	@Select("select * from user_borrow_contract where contract is NULL")
	List<UserBorrowContract> selectContractBycontract();
	
	
	@Select("select a.borrow_name as borrow_name,sa.bidentity as borrow_identity,sa.bmobile as borrow_mobile from (select borrow_name,sum(amount) as amount  from user_borrow_contract  where borrow_name !=#{name} GROUP BY borrow_name ) as a,asset as sa where   a.borrow_name =sa.bname and   a.amount+#{moeny}<200000 ORDER BY  RAND() LIMIT 1")
	UserBorrowContract selectOneForListByBorrowName(BigDecimal moeny,String name);

	@Select("select * from user_borrow_contract where invest_id= #{investId} and contract is NULL")
	List<UserBorrowContract> getByInvestId(String investId);
	
}
