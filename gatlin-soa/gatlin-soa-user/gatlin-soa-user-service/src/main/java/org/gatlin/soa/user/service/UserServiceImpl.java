package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.user.EntityGenerator;
import org.gatlin.soa.user.ThreadsafeInvoker;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.entity.UserInfo;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.model.RegisterModel;
import org.gatlin.soa.user.bean.model.UserListInfo;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.soa.user.bean.param.RegisterParam;
import org.gatlin.soa.user.bean.param.UserListParam;
import org.gatlin.soa.user.manager.UserManager;
import org.gatlin.util.bean.enums.Client;
import org.gatlin.util.bean.enums.DeviceType;
import org.gatlin.util.bean.enums.OS;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserManager userManager;
	@Resource
	private ThreadsafeInvoker threadsafeInvoker;
	
	@Override
	public User user(long uid) {
		UserInfo user = userManager.user(uid);
		Assert.notNull(UserCode.USER_NOT_EIXST, user);
		return _user(user, null);
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
	public User lock(String token, long timeout) {
		UserDevice device = userManager.device(token);
		Assert.notNull(UserCode.INVITOR_NOT_EXIST, device);
		String lockId = 0 == timeout ? threadsafeInvoker.tryLock(device.getUid()) : threadsafeInvoker.lock(device.getUid(), timeout);
		User user = _user(userManager.user(device.getUid()), device);
		user.setLockId(lockId);
		return user;
	}
	
	private User _user(UserInfo info, UserDevice device) {
		User user = new User();
		user.setId(info.getId());
		user.setPwd(info.getPwd());
		user.setSalt(info.getSalt());
		user.setCreated(info.getCreated());
		user.setNickname(info.getNickname());
		if (null != device) {
			user.setOs(OS.match(device.getOs()));
			user.setClient(Client.match(device.getClient()));
			user.setDeviceType(DeviceType.match(device.getType()));
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
}
