package org.gatlin.soa.user;

import java.text.MessageFormat;

public class KeyGenerator {

	private static final String USER_LOCK			= "string:lock:user:{0}";
	
	public static final String userLockKey(long uid) {
		return MessageFormat.format(USER_LOCK, String.valueOf(uid));
	}
}
