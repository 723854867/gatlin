package org.gatlin.soa.user.manager;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.entity.UserDevice;
import org.gatlin.soa.entity.UserInfo;
import org.gatlin.soa.user.EntityGenerator;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.entity.UserInvitation;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.info.LoginInfo;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.model.RegisterModel;
import org.gatlin.soa.user.bean.model.query.DeviceQuery;
import org.gatlin.soa.user.bean.model.query.UsernameQuery;
import org.gatlin.soa.user.bean.param.RegisterParam;
import org.gatlin.soa.user.mybatis.dao.UserDeviceDao;
import org.gatlin.soa.user.mybatis.dao.UserInfoDao;
import org.gatlin.soa.user.mybatis.dao.UserInvitationDao;
import org.gatlin.soa.user.mybatis.dao.UsernameDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserManager {

	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private UsernameDao usernameDao;
	@Resource
	private UserDeviceDao userDeviceDao;
	@Resource
	private UserInvitationDao userInvitationDao;

	@Transactional
	public RegisterModel register(RegisterParam param) {
		UserInfo user = EntityGenerator.newUserInfo(param.getPassword());
		userInfoDao.insert(user);
		Username username = EntityGenerator.newUsername(user, param.getUsername(), param.getUsernameType());
		usernameDao.insert(username);
		UserInvitation invitation = null;
		if (null != param.getInviter()) {
			UserInfo invitor = userInfoDao.getByKey(param.getInviter());
			Assert.notNull(UserCode.INVITOR_NOT_EXIST, invitor);
			invitation = EntityGenerator.newUserInvitation(invitor, user);
			userInvitationDao.insert(invitation);
		}
		return new RegisterModel(user.getId(), invitation);
	}
	
	@Transactional
	public LoginModel login(UserDevice device, String pwd) {
		UserInfo user = userInfoDao.getByKey(device.getUid());
		String cpwd = DigestUtils.md5Hex(pwd + "_" + user.getSalt());
		Assert.isTrue(cpwd.equalsIgnoreCase(pwd), UserCode.LOGIN_PWD_ERROR);
		DeviceQuery query = new DeviceQuery();
		query.uid(user.getId()).type(device.getType());
		UserDevice odevice = userDeviceDao.queryUnique(query);
		if (null != odevice)							// 已经有同类型的设备登录了
			userDeviceDao.deleteByKey(odevice.getId());
		userDeviceDao.insert(device);
		return new LoginModel(new LoginInfo(user.getId(), device.getToken()), device, odevice);
	}
	
	public UserInfo user(long uid) {
		return userInfoDao.getByKey(uid);
	}
	
	public UserDevice device(String id) {
		return userDeviceDao.getByKey(id);
	}
	
	public Username username(UsernameType type, String username) {
		UsernameQuery query = new UsernameQuery();
		query.username(username).type(type);
		return usernameDao.queryUnique(query);
	}
}
