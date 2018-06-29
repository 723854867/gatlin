package org.gatlin.sdk.amazon.request;

import org.gatlin.sdk.amazon.AmazonConfig;
import org.gatlin.sdk.amazon.response.AmazonResponse;

public abstract class SellerRequest<RESPONSE extends AmazonResponse, REQUEST extends SellerRequest<RESPONSE, REQUEST>> extends AmazonRequest<RESPONSE, REQUEST> {

	public SellerRequest(String action, String version, String path) {
		super(action, version, path);
		sellerId(AmazonConfig.sellerId());
	}
	
	// 卖家编号
	public REQUEST sellerId(String sellerId ) {
		return _addParam("SellerId", sellerId );
	}
}
