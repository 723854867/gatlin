package org.gatlin.soa.user;

import org.gatlin.soa.user.bean.UserUtil;
import org.gatlin.soa.user.bean.entity.UserAddress;
import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.entity.UserInfo;
import org.gatlin.soa.user.bean.entity.UserInvitation;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.KeyUtil;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final UserInfo newUserInfo(String password) {
		UserInfo instance = new UserInfo();
		instance.setNickname(StringUtil.EMPTY);
		instance.setSalt(KeyUtil.randomCode(6, false));
		instance.setPwd(UserUtil.pwd(password, instance.getSalt()));
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final Username newUsername(UserInfo user, String username, UsernameType type) {
		Username instance = new Username();
		instance.setUid(user.getId());
		instance.setType(type.mark());
		instance.setUsername(username);
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final UserInvitation newUserInvitation(UserInfo invitor, UserInfo invitee) {
		UserInvitation instance = new UserInvitation();
		instance.setInvitee(invitee.getId());
		instance.setInvitor(invitor.getId());
		instance.setCreated(DateUtil.current());
		instance.setId(UserUtil.inviteKey(invitor, invitee));
		return instance;
	}
	
	public static final UserDevice newUserDevice(Username username, LoginParam param) {
		UserDevice instance = new UserDevice();
		instance.setToken(StringUtil.uuid());
		instance.setUid(username.getId());
		instance.setUsername(username.getId());
		instance.setOs(param.getOs().mark());
		instance.setType(param.getDeviceType().mark());
		instance.setClient(param.getClient().mark());
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final UserAddress newUserAddress(AddressAddparam param) {
		UserAddress instance = new UserAddress();
		instance.setUid(param.getUser().getId());
		instance.setCity(param.getCity());
		instance.setCounty(param.getCounty());
		instance.setProvince(param.getProvince());
		instance.setUsed(param.isUsed());
		instance.setMemo(param.getMemo());
		instance.setDetail(param.getDetail());
		instance.setContacts(param.getContacts());
		instance.setContactsMobile(param.getContactsMobile());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
