package org.gatlin.sdk.jpush;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public class JPushConfig {

	public static final int port() {
		return GatlinConfigration.get(PORT);
	}

	public static final String host() {
		return GatlinConfigration.get(HOST);
	}
	
	public static final String appKey() {
		return GatlinConfigration.get(APP_KEY);
	} 
	
	public static final String masterSecret() {
		return GatlinConfigration.get(MASTER_SECRET);
	} 
	
	private static final StrOption HOST								= new StrOption("jpush.host");
	private static final IntegerOption PORT							= new IntegerOption("jpush.port", 80);
	private static final StrOption APP_KEY							= new StrOption("jpush.app.key");
	private static final StrOption MASTER_SECRET					= new StrOption("jpush.master.secret");
}
