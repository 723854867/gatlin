package org.gatlin.sdk.jisu;

import java.lang.reflect.Type;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;
import org.gatlin.sdk.jisu.result.Calendar;
import org.gatlin.sdk.jisu.result.JieQi;

import com.google.gson.reflect.TypeToken;

public class JisuConfig {
	
	public static final int port() {
		return GatlinConfigration.get(PORT);
	}
	
	public static final String host() {
		return GatlinConfigration.get(HOST);
	}
	
	public static final String appKey() {
		return GatlinConfigration.get(APP_KEY);
	}
	
	private static final StrOption HOST									= new StrOption("jisu.host");
	private static final IntegerOption PORT								= new IntegerOption("jisu.port");
	private static final StrOption APP_KEY								= new StrOption("jisu.appKey");
	
	public static final Type JIE_QI = new TypeToken<JisuResponse<JieQi>>() {}.getType();
	public static final Type STRING = new TypeToken<JisuResponse<String>>() {}.getType();
	public static final Type WAN_NIAN_LI = new TypeToken<JisuResponse<Calendar>>() {}.getType();
}
