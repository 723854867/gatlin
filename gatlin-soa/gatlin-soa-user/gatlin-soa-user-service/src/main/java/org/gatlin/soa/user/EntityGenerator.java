package org.gatlin.soa.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.gatlin.soa.bean.enums.DeviceType;
import org.gatlin.soa.entity.UserDevice;
import org.gatlin.soa.entity.UserInfo;
import org.gatlin.soa.user.bean.entity.UserInvitation;
import org.gatlin.soa.user.bean.entity.Username;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.KeyUtil;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final UserInfo newUserInfo(String password) {
		UserInfo instance = new UserInfo();
		instance.setAvatar(StringUtil.EMPTY);
		instance.setNickname(StringUtil.EMPTY);
		instance.setId(IDWorker.INSTANCE.nextId());
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
	
	public static final UserDevice newUserDevice(long uid, DeviceType type, String deviceId) {
		UserDevice instance = new UserDevice();
		instance.setId(deviceId);
		instance.setUid(uid);
		instance.setType(type.mark());
		instance.setCreated(DateUtil.current());
		return instance;
	}
}
