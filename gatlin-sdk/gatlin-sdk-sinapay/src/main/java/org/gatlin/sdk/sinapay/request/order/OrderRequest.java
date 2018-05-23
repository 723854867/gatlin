package org.gatlin.sdk.sinapay.request.order;

import org.gatlin.sdk.sinapay.SinapayConfig;
import org.gatlin.sdk.sinapay.request.SinapayRequest;
import org.gatlin.sdk.sinapay.response.SinapayResponse;

class OrderRequest<RESPONSE extends SinapayResponse, REQUEST extends OrderRequest<RESPONSE, REQUEST>> extends SinapayRequest<RESPONSE, REQUEST> {

	public OrderRequest() {
		super(SinapayConfig.orderHost(), SinapayConfig.orderPath());
	}
	
	public abstract static class Builder<RESPONSE extends SinapayResponse, REQUEST extends OrderRequest<RESPONSE, REQUEST>, BUILDER extends Builder<RESPONSE, REQUEST, BUILDER>> extends SinapayRequest.Builder<RESPONSE, REQUEST, BUILDER> {
		
		private static final long serialVersionUID = -4775652927768932559L;
		
		protected Builder(String service) {
			super(service);
		}
	}
}
