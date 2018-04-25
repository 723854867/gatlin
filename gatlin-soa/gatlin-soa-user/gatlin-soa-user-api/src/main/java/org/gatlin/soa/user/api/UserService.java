package org.gatlin.soa.user.api;

import org.gatlin.soa.bean.User;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.info.UserTips;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.model.RegisterModel;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.soa.user.bean.param.RegisterParam;

/**
 * 用户接口
 * 
 * @author lynn
 */
public interface UserService {

	User user(String token);
	
	UserTips user(UsernameType type, String username);
	
	// 通过 token 获取用户锁
	User lock(String token, long timeout);
	
	// 登录
	LoginModel login(LoginParam param);
	
	// 释放用户锁
	boolean releaseLock(long uid, String lockId);
	
	// 新建用户(注册)
	RegisterModel register(RegisterParam param);
}
