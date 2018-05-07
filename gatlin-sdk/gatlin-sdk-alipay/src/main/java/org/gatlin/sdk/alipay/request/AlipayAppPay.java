package org.gatlin.sdk.alipay.request;

import org.gatlin.sdk.alipay.response.AlipayResponse;

/**
 * APP支付
 * 
 * @author lynn
 */
public class AlipayAppPay extends AlipayRequest<AlipayResponse, AlipayAppPay> {

	public AlipayAppPay(String host, int port, String path) {
		super(host, port, path);
	}
	
	public static class Builder extends AlipayRequest.Builder<Builder> {

		private static final long serialVersionUID = -4885533906254982244L;

		public Builder() {
			super("alipay.trade.app.pay");
		}
	}
}
