package org.gatlin.skd.chuanglan;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public class ChuangLanConfig {
	
	public static final int port() {
		return GatlinConfigration.get(PORT);
	}

	public static final String host() {
		return GatlinConfigration.get(HOST);
	}
	
	public static final String account() {
		return GatlinConfigration.get(ACCOUNT);
	} 
	
	public static final String password() {
		return GatlinConfigration.get(PASSWORD);
	}
	
	private static final StrOption HOST								= new StrOption("chuanglan.host");
	private static final IntegerOption PORT							= new IntegerOption("chuanglan.port", 80);
	private static final StrOption ACCOUNT							= new StrOption("chuanglan.account");
	private static final StrOption PASSWORD							= new StrOption("chuanglan.password");
}
