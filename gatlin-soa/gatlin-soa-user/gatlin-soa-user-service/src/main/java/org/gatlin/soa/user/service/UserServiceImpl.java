package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.courier.api.EmailService;
import org.gatlin.soa.courier.api.SmsService;
import org.gatlin.soa.user.ThreadsafeInvoker;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.entity.UserInfo;
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
import org.gatlin.soa.user.manager.UserManager;
import org.gatlin.soa.user.mybatis.EntityGenerator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private SmsService smsService;
	@Resource
	private UserManager userManager;
	@Resource
	private EmailService emailService;
	@Resource
	private ThreadsafeInvoker threadsafeInvoker;
	
	@Override
	public User user(long uid) {
		UserInfo user = userManager.user(uid);
		return null == user ? null : _user(user, null);
	}
	
	@Override
	public User user(String token) {
		UserDevice device = userManager.device(token);
		Assert.notNull(UserCode.INVALID_TOKEN, device);
		return _user(userManager.user(device.getUid()), device);
	}
	
	@Override
	public User user(UsernameType type, String username) {
		Username uname = userManager.username(type, username);
		if (null == uname)
			return null;
		UserInfo user = userManager.user(uname.getUid());
		return _user(user, null);
	}
	
	@Override
	public User lock(long uid, long timeout) {
		UserInfo userInfo = userManager.user(uid);
		Assert.notNull(UserCode.USER_NOT_EIXST, userInfo);
		String lockId = 0 == timeout ? threadsafeInvoker.tryLock(uid) : threadsafeInvoker.lock(uid, timeout);
		User user = _user(userInfo, null);
		user.setLockId(lockId);
		return user;
	}
	
	@Override
	public User lock(String token, long timeout) {
		UserDevice device = userManager.device(token);
		Assert.notNull(UserCode.USER_UNLOGIN, device);
		String lockId = 0 == timeout ? threadsafeInvoker.tryLock(device.getUid()) : threadsafeInvoker.lock(device.getUid(), timeout);
		User user = _user(userManager.user(device.getUid()), device);
		user.setLockId(lockId);
		return user;
	}
	
	private User _user(UserInfo info, UserDevice device) {
		User user = new User();
		user.setId(info.getId());
		user.setMod(info.getMod());
		user.setPwd(info.getPwd());
		user.setSalt(info.getSalt());
		user.setCreated(info.getCreated());
		user.setNickname(info.getNickname());
		if (null != device) {
			user.setOs(device.getOs());
			user.setClient(device.getClient());
			user.setDeviceType(device.getType());
		}
		return user;
	}
	
	@Override
	public boolean releaseLock(long uid, String lockId) {
		return threadsafeInvoker.releaseLock(uid, lockId);
	}
	
	@Override
	public void update(User user) {
		userManager.update(user);
	}

	@Override
	public RegisterModel register(RegisterParam param) {
		try {
			return userManager.register(param);
		} catch (DuplicateKeyException e) {
			throw new CodeException(UserCode.USERNAME_EXIST);
		}
	}
	
	@Override
	public LoginModel login(LoginParam param) {
		Username username = userManager.username(param.getUsernameType(), param.getUsername());
		Assert.notNull(UserCode.USERNAME_NOT_EXIST, username);
		UserDevice device = EntityGenerator.newUserDevice(username, param);
		return threadsafeInvoker.invoke(username.getUid(), () -> {
			return userManager.login(device, param.getPassword());
		});
	}
	
	@Override
	public void logout(String token) {
		userManager.logout(token);
	}
	
	@Override
	public Pager<UserListInfo> users(UserListParam param) {
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return new Pager<UserListInfo>(userManager.users(param));
	}
	
	@Override
	public Username username(Query query) {
		return userManager.username(query);
	}
	
	@Override
	public Pager<Username> usernames(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<Username>(userManager.usernames(query));
	}
	
	@Override
	public void usernameReset(UsernameResetParam param) {
		switch (param.getUsernameType()) {
		case EMAIL:
			emailService.captchaVerify(param.getUsername(), param.getCaptcha());
			emailService.captchaVerify(param.getNusername(), param.getNcaptcha());
			break;
		case MOBILE:
			smsService.captchaVerify(param.getUsername(), param.getCaptcha());
			smsService.captchaVerify(param.getNusername(), param.getNcaptcha());
			break;
		default:
			break;
		}
		userManager.usernameReset(param);
	}
	
	@Override
	public UserSecurity realname(RealnameParam param) {
		return userManager.realname(param);
	}
	
	@Override
	public UserSecurity security(long uid) {
		return userManager.security(uid);
	}
	
	@Override
	public Pager<UserSecurity> securities(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<UserSecurity>(userManager.securities(query));
	}
}
