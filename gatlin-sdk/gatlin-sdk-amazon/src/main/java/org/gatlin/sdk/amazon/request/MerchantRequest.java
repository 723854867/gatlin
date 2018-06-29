package org.gatlin.sdk.amazon.request;

import org.gatlin.sdk.amazon.AmazonConfig;
import org.gatlin.sdk.amazon.response.AmazonResponse;

public class MerchantRequest<RESPONSE extends AmazonResponse, REQUEST extends MerchantRequest<RESPONSE, REQUEST>> extends AmazonRequest<RESPONSE, REQUEST> {

	public MerchantRequest(String action, String version, String path) {
		super(action, version, path);
		merchant(AmazonConfig.sellerId());
	}

	// 卖家编号
	public REQUEST merchant(String merchant) {
		return _addParam("Merchant", merchant);
	}
}
