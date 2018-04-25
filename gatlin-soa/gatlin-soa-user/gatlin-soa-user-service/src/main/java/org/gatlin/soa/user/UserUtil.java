package org.gatlin.soa.user;

import org.gatlin.soa.user.bean.entity.UserInfo;

public class UserUtil {

	public static final String inviteKey(UserInfo invitor, UserInfo invitee) {
		return invitor.getId() > invitee.getId() ? invitee.getId() + "_" + invitor.getId() : invitor.getId() + "_" + invitee.getId();
	}
}
