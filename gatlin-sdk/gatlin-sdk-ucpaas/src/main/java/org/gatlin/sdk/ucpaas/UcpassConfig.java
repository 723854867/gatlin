package org.gatlin.sdk.ucpaas;

import org.gatlin.core.GatlinConfigration;

public class UcpassConfig {
	
	public static final String sid() {
		return GatlinConfigration.get(UcpassConsts.Options.SID);
	}
	
	public static final String token() {
		return GatlinConfigration.get(UcpassConsts.Options.TOKEN);
	}
	
	public static final String appId() {
		return GatlinConfigration.get(UcpassConsts.Options.APPID);
	}

	public static final int port() {
		return GatlinConfigration.get(UcpassConsts.Options.PORT);
	}
	
	public static final String host() {
		return GatlinConfigration.get(UcpassConsts.Options.HOST);
	}
	
	public static final String pathSendSms() {
		return GatlinConfigration.get(UcpassConsts.Options.PATH_SEND_SMS);
	}
	
	public static final String pathCreateSmsTemplate() {
		return GatlinConfigration.get(UcpassConsts.Options.PATH_CREATE_SMS_TEMPLATE);
	}
}
