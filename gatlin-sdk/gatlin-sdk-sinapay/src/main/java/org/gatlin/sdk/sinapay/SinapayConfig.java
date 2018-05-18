package org.gatlin.sdk.sinapay;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.StrOption;

public class SinapayConfig {

	public static final String orderHost() {
		return GatlinConfigration.get(ORDER_HOST);
	}

	public static final String memberHost() {
		return GatlinConfigration.get(MEMBER_HOST);
	}
	
	public static final String orderPath() {
		return GatlinConfigration.get(ORDER_PATH);
	}
	
	public static final String memberPath() {
		return GatlinConfigration.get(MEMBER_PATH);
	}
	
	// 新浪公钥
	public static final String pubKey() {
		return GatlinConfigration.get(PUB_KEY);
	} 
	
	// 新浪商户公钥
	public static final String merchantPubKey() {
		return GatlinConfigration.get(MERCHANT_PUB_KEY);
	} 
	
	// 新浪商户私钥
	public static final String merchantPriKey() {
		return GatlinConfigration.get(MERCHANT_PRI_KEY);
	}
	
	public static final String partnerId() {
		return GatlinConfigration.get(PARTENER_ID);
	}
	
	private static final StrOption ORDER_HOST						= new StrOption("sinapay.order.host");
	private static final StrOption MEMBER_HOST						= new StrOption("sinapay.member.host");
	private static final StrOption ORDER_PATH						= new StrOption("sinapay.order.path");
	private static final StrOption MEMBER_PATH						= new StrOption("sinapay.member.path");
	private static final StrOption PUB_KEY							= new StrOption("sinapay.pubKey");
	private static final StrOption MERCHANT_PUB_KEY					= new StrOption("sinapay.merchant.pubKey");
	private static final StrOption MERCHANT_PRI_KEY					= new StrOption("sinapay.merchant.priKey");
	private static final StrOption PARTENER_ID						= new StrOption("sinapay.partnerId");
}
