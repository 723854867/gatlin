package org.gatlin.soa.account.mybatis.dao;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.account.bean.entity.Account;

public interface AccountDao extends DBDao<Long, Account> {

	@MapKey("owner")
	Map<Long, Account> getByUserIds(@Param("userIds") Set<Long> userIds);

	@Select("SELECT * FROM account WHERE TYPE = 1 AND owner_type = 1 AND OWNER = #{userId}")
	Account getByUserId(@Param("userId")long userId);

}
