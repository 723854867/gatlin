package org.gatlin.sdk.alipay;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.StrOption;

public class AlipayConfig {

	public static final String appId() {
		return GatlinConfigration.get(APP_ID);
	} 
	
	private static final StrOption APP_ID								= new StrOption("alipay.appId");
}
