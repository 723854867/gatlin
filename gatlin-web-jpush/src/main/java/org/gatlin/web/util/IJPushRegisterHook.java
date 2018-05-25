package org.gatlin.web.util;

import org.gatlin.soa.jpush.bean.entity.JPushDevice;

public interface IJPushRegisterHook {

	void register(String registrationId, JPushDevice odevice);
}
