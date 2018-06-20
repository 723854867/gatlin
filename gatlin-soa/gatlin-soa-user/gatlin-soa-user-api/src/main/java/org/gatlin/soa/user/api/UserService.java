package org.gatlin.soa.user.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.user.bean.entity.UserSecurity;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.model.RegisterModel;
import org.gatlin.soa.user.bean.model.UserListInfo;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.soa.user.bean.param.RealnameParam;
import org.gatlin.soa.user.bean.param.RegisterParam;
import org.gatlin.soa.user.bean.param.UserListParam;
import org.gatlin.soa.user.bean.param.UsernameResetParam;

/**
 * 用户接口
 * 
 * @author lynn
 */
public interface UserService {
	
	User user(long uid);

	User user(String token);
	
	User user(UsernameType type, String username);
	
	User lock(long uid, long timeout);
	
	// 通过 token 获取用户锁
	User lock(String token, long timeout);
	
	// 登录
	LoginModel login(LoginParam param);
	
	void logout(String token);
	
	// 释放用户锁
	boolean releaseLock(long uid, String lockId);
	
	void update(User user);
	
	// 新建用户(注册)
	RegisterModel register(RegisterParam param);
	
	Pager<UserListInfo> users(UserListParam param); 
	
	Username username(Query query);
	
	Pager<Username> usernames(Query query);
	
	// 重置账号
	void usernameReset(UsernameResetParam param);
	
	UserSecurity realname(RealnameParam param);
	
	UserSecurity security(long uid);
	
	Pager<UserSecurity> securities(Query query);
}
