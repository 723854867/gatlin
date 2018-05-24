package org.gatlin.sdk.jiguang;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public class JiGuangConfig {

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
	
	public static final String pathPush() {
		return GatlinConfigration.get(PATH_PUSH);
	} 
	
	public static final String pathPushValidate() {
		return GatlinConfigration.get(PATH_PUSH_VALIDATE);
	} 
	
	private static final StrOption HOST								= new StrOption("jiguang.host");
	private static final IntegerOption PORT							= new IntegerOption("jiguang.port", 80);
	private static final StrOption APP_KEY							= new StrOption("jiguang.app.key");
	private static final StrOption MASTER_SECRET					= new StrOption("jiguang.master.secret");
	private static final StrOption PATH_PUSH						= new StrOption("jiguang.path.push");
	private static final StrOption PATH_PUSH_VALIDATE				= new StrOption("jiguang.path.push.validate");
}
