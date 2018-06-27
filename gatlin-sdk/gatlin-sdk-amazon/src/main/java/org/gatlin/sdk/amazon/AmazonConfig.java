package org.gatlin.sdk.amazon;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public class AmazonConfig {
	
	public static final int port() {
		return GatlinConfigration.get(PORT);
	}
	
	public static final String host() {
		return GatlinConfigration.get(HOST);
	}
	
	public static final String sellerId() {
		return GatlinConfigration.get(SELLER_ID);
	}
	
	public static final String secretKey() {
		return GatlinConfigration.get(SECRET_KEY);
	}
	
	public static final String marketplaceId() {
		return GatlinConfigration.get(MARKETPLACE_ID);
	}
	
	public static final String AWSAccessKeyId() {
		return GatlinConfigration.get(AWS_ACCESS_KEY_ID);
	}

	private static final StrOption HOST								= new StrOption("amazon.host");
	private static final StrOption SECRET_KEY						= new StrOption("amazon.secret.key");
	private static final StrOption AWS_ACCESS_KEY_ID				= new StrOption("amazon.AWS.access.key.id");
	private static final StrOption MARKETPLACE_ID					= new StrOption("amazon.marketplace.id");
	private static final StrOption SELLER_ID						= new StrOption("amazon.seller.id");
	private static final IntegerOption PORT							= new IntegerOption("amazon.port", 443);

}
