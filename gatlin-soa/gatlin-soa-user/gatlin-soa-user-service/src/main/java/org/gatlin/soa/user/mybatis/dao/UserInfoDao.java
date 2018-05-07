package org.gatlin.soa.user.mybatis.dao;

import java.util.List;

import org.gatlin.dao.mybatis.DBDao;
import org.gatlin.soa.user.bean.entity.UserInfo;
import org.gatlin.soa.user.bean.model.UserListInfo;
import org.gatlin.soa.user.bean.param.UserListParam;

public interface UserInfoDao extends DBDao<Long, UserInfo> {

	List<UserListInfo> list(UserListParam param);
}
