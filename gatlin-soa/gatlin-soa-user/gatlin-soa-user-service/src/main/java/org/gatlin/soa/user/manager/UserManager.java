package org.gatlin.soa.user.manager;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.user.EntityGenerator;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.soa.user.bean.UserUtil;
import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.entity.UserInfo;
import org.gatlin.soa.user.bean.entity.UserInvitation;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.info.LoginInfo;
import org.gatlin.soa.user.bean.model.LoginModel;
import org.gatlin.soa.user.bean.model.RegisterModel;
import org.gatlin.soa.user.bean.param.RegisterParam;
import org.gatlin.soa.user.mybatis.dao.UserDeviceDao;
import org.gatlin.soa.user.mybatis.dao.UserInfoDao;
import org.gatlin.soa.user.mybatis.dao.UserInvitationDao;
import org.gatlin.soa.user.mybatis.dao.UsernameDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;
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
		String cpwd = UserUtil.pwd(pwd, user.getSalt());
		Assert.isTrue(UserCode.LOGIN_PWD_ERROR, cpwd.equalsIgnoreCase(user.getPwd()));
		Query query = new Query().eq("uid", user.getId()).eq("type", device.getType());
		UserDevice odevice = userDeviceDao.queryUnique(query);
		if (null != odevice)							// 已经有同类型的设备登录了
			userDeviceDao.deleteByKey(odevice.getToken());
		userDeviceDao.insert(device);
		return new LoginModel(new LoginInfo(user.getId(), device.getToken()), device, odevice);
	}
	
	public void update(User user) {
		UserInfo info = userInfoDao.getByKey(user.getId());
		if (StringUtil.hasText(user.getPwd()))
			info.setPwd(user.getPwd());
		if (StringUtil.hasText(user.getNickname()))
			info.setNickname(user.getNickname());
		info.setUpdated(DateUtil.current());
		userInfoDao.update(info);
	}
	
	public UserInfo user(long uid) {
		return userInfoDao.getByKey(uid);
	}
	
	public UserDevice device(String token) {
		return userDeviceDao.getByKey(token);
	}
	
	public Username username(UsernameType type, String username) {
		Query query = new Query().eq("username", username).eq("type", type.mark());
		return usernameDao.queryUnique(query);
	}
	
	public List<UserInfo> users(Query query) {
		return userInfoDao.queryList(query);
	}
}
