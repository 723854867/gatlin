package org.gatlin.sdk.alipay.request;

import java.util.Map;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.alipay.AlipayConfig;
import org.gatlin.sdk.alipay.response.AlipayResponse;
import org.gatlin.util.DateUtil;
import org.gatlin.util.serial.SerializeUtil;

import com.google.gson.annotations.SerializedName;

public class AlipayRequest<RESPONSE extends AlipayResponse, REQUEST extends AlipayRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public AlipayRequest(String host, int port, String path) {
		super(host, port, path);
	}
	
	public static class Builder<BUILDER extends Builder<BUILDER>> implements org.gatlin.core.service.http.HttpPost.Body {

		private static final long serialVersionUID = 7491685951687117463L;
		
		@SerializedName("app_id")
		protected String appIdd;
		protected String method;
		protected String format = "JSON";
		protected String charset = "utf-8";
		protected String sign_type = "RSA2";
		protected String sign;
		protected String timestamp = DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS);
		protected String version = "1.0";
		protected String notify_url;
		protected String biz_content;
		
		public Builder(String metohd) {
			this.method = metohd;
			this.appIdd = AlipayConfig.appId();
		}
		
		@SuppressWarnings("unchecked")
		public BUILDER notifyUrl(String notifyUrl) {
			this.notify_url = notifyUrl;
			return (BUILDER) this;
		}
		
		public static void main(String[] args) {
			Builder<?> builder = new Builder<>("s");
			String json = SerializeUtil.GSON.toJson(builder);
			Map<String, String>
		}
	}
}
