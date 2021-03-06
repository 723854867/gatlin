package org.gatlin.sdk.alipay;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.message.WrapResponse;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public class AlipayConfig {
	
	public static final WrapResponse SUCCESS = new WrapResponse("success");

	public static final String appId() {
		return GatlinConfigration.get(APP_ID);
	} 
	
	public static final String host() {
		return GatlinConfigration.get(HOST);
	} 
	
	public static final int port() {
		return GatlinConfigration.get(PORT);
	} 
	
	public static final String path() {
		return GatlinConfigration.get(PATH);
	} 
	
	public static final String pubKey() {
		return GatlinConfigration.get(PUB_KEY);
	} 
	
	public static final String merchantPriKey() {
		return GatlinConfigration.get(MERCHANT_PRI_KEY);
	} 
	
	public static final String notifyUrlRecharge() {
		return GatlinConfigration.get(RECHARGE_NOTIFY_URL);
	} 
	
	private static final StrOption APP_ID							= new StrOption("alipay.appid");
	private static final StrOption HOST								= new StrOption("alipay.host");
	private static final StrOption PATH								= new StrOption("alipay.path");
	private static final StrOption PUB_KEY							= new StrOption("alipay.pubKey");
	private static final StrOption MERCHANT_PRI_KEY					= new StrOption("alipay.merchant.priKey");
	private static final IntegerOption PORT							= new IntegerOption("alipay.port");
	private static final StrOption RECHARGE_NOTIFY_URL				= new StrOption("alipay.notify.url.recharge");
}
