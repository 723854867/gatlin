package org.gatlin.sdk.ucpaas;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public class UcpassConfig {
	
	public static final String sid() {
		return GatlinConfigration.get(SID);
	}
	
	public static final String token() {
		return GatlinConfigration.get(TOKEN);
	}
	
	public static final String appId() {
		return GatlinConfigration.get(APPID);
	}

	public static final int port() {
		return GatlinConfigration.get(PORT);
	}
	
	public static final String host() {
		return GatlinConfigration.get(HOST);
	}
	
	public static final String pathSendSms() {
		return GatlinConfigration.get(PATH_SEND_SMS);
	}
	
	public static final String pathCreateSmsTemplate() {
		return GatlinConfigration.get(PATH_CREATE_SMS_TEMPLATE);
	}
	
	private static final StrOption SID									= new StrOption("ucpass.sid");
	private static final StrOption HOST									= new StrOption("ucpass.host");
	private static final StrOption TOKEN								= new StrOption("ucpass.token");
	private static final StrOption APPID								= new StrOption("ucpass.appid");
	private static final IntegerOption PORT								= new IntegerOption("ucpass.port");
	private static final StrOption PATH_SEND_SMS						= new StrOption("ucpass.path.send.sms");
	private static final StrOption PATH_CREATE_SMS_TEMPLATE				= new StrOption("ucpass.path.create.sms.template");
}
