package org.gatlin.soa.account.mybatis.dao;

import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.account.bean.entity.Recharge;

public interface RechargeDao extends DBDao<String, Recharge> {

	@Select("SELECT COUNT(*) FROM recharge WHERE recharger=#{uid} AND state IN(3,5)")
	int userSuccessCount(long uid);
}
