package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.entity.UserDevice;
import org.gatlin.soa.model.User;
import org.gatlin.soa.user.EntityGenerator;
import org.gatlin.soa.user.ThreadsafeInvoker;
import org.gatlin.soa.user.UserUtil;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.model.RegisterModel;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.soa.user.bean.param.RegisterParam;
import org.gatlin.soa.user.manager.UserManager;
import org.gatlin.util.codec.Decrypt;
import org.gatlin.util.codec.Encrypt;
import org.gatlin.util.serial.SerializeUtil;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserManager userManager;
	@Resource
	private ThreadsafeInvoker threadsafeInvoker;
	
	@Override
	public User user(String token) {
		String key = GatlinConfigration.get(UserUtil.USER_TOKEN_SECRET_KEY);
		String json = Decrypt.AESDecode(key, token);
		UserDevice device = SerializeUtil.GSON.fromJson(json, UserDevice.class);
		UserDevice odevice = userManager.device(device.getId());
		Assert.isTrue(null != odevice && odevice.getToken().equals(token) && odevice.getUid() == device.getUid(), UserCode.USER_UNLOGIN);
		return new User(userManager.user(device.getUid()), device);
	}
	
	@Override
	public User lock(String token, long timeout) {
		String key = GatlinConfigration.get(UserUtil.USER_TOKEN_SECRET_KEY);
		String json = Decrypt.AESDecode(key, token);
		UserDevice device = SerializeUtil.GSON.fromJson(json, UserDevice.class);
		String lockId = 0 == timeout ? threadsafeInvoker.tryLock(device.getUid()) : threadsafeInvoker.lock(device.getUid(), timeout);
		UserDevice odevice = userManager.device(device.getId());
		Assert.isTrue(null != odevice && odevice.getToken().equals(token) && odevice.getUid() == device.getUid(), UserCode.USER_UNLOGIN);
		return new User(lockId, userManager.user(device.getUid()), device);
	}
	
	@Override
	public boolean releaseLock(long uid, String lockId) {
		return threadsafeInvoker.releaseLock(uid, lockId);
	}

	@Override
	public RegisterModel register(RegisterParam param) {
		return userManager.register(param);
	}
	
	@Override
	public LoginModel login(LoginParam param) {
		Username username = userManager.username(param.getUsernameType(), param.getUsername());
		Assert.notNull(UserCode.USERNAME_NOT_EXIST, username);
		UserDevice device = EntityGenerator.newUserDevice(username.getUid(), param.getDeviceType(), param.getDeviceId());
		String key = GatlinConfigration.get(UserUtil.USER_TOKEN_SECRET_KEY);
		String token = Encrypt.AESEncode(key, SerializeUtil.GSON.toJson(device));
		device.setToken(token);
		return threadsafeInvoker.invoke(username.getUid(), () -> {
			return userManager.login(device, param.getPassword());
		});
	}
}
