package org.gatlin.soa.user;

import org.gatlin.core.bean.model.option.StrOption;
import org.gatlin.soa.entity.UserInfo;

public class UserUtil {

	public static final StrOption USER_TOKEN_SECRET_KEY				= new StrOption("user.tokenSecretKey"); 
	
	public static final String inviteKey(UserInfo invitor, UserInfo invitee) {
		return invitor.getId() > invitee.getId() ? invitee.getId() + "_" + invitor.getId() : invitor.getId() + "_" + invitee.getId();
	}
}
