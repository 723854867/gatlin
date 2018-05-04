package org.gatlin.soa.user.bean;

import org.apache.commons.codec.digest.DigestUtils;
import org.gatlin.soa.user.bean.entity.UserInfo;

public class UserUtil {
	
	public static final String pwd(String pwd, String salt) {
		return DigestUtils.md5Hex(pwd + "_" + salt);
	}

	public static final String inviteKey(UserInfo invitor, UserInfo invitee) {
		return invitor.getId() > invitee.getId() ? invitee.getId() + "_" + invitor.getId() : invitor.getId() + "_" + invitee.getId();
	}
}
