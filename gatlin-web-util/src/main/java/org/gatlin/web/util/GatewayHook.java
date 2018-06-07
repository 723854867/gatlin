package org.gatlin.web.util;

import org.gatlin.soa.bean.User;

public interface GatewayHook {

	void filter(User user, Object[] params);
}
