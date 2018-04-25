package org.gatlin.soa.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.gatlin.soa.user.bean.entity.UserDevice;
import org.gatlin.soa.user.bean.entity.UserInfo;
import org.gatlin.soa.user.bean.entity.UserInvitation;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.soa.user.bean.param.LoginParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.KeyUtil;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final UserInfo newUserInfo(String password) {
		UserInfo instance = new UserInfo();
		instance.setAvatar(StringUtil.EMPTY);
		instance.setNickname(StringUtil.EMPTY);
		instance.setSalt(KeyUtil.randomCode(6, false));
		instance.setPwd(DigestUtils.md5Hex(password + "_" + instance.getSalt()));
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
	
	public static final UserDevice newUserDevice(long uid, LoginParam param) {
		UserDevice instance = new UserDevice();
		instance.setToken(StringUtil.uuid());
		instance.setUid(uid);
		instance.setOs(param.getOs().mark());
		instance.setType(param.getDeviceType().mark());
		instance.setClient(param.getClient().mark());
		instance.setCreated(DateUtil.current());
		return instance;
	}
}
