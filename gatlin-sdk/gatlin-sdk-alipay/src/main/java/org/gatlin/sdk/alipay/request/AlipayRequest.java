package org.gatlin.sdk.alipay.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.alipay.AlipayConfig;
import org.gatlin.sdk.alipay.response.AlipayResponse;
import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.enums.Protocol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlipayRequest<RESPONSE extends AlipayResponse, REQUEST extends AlipayRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public AlipayRequest() {
		super(AlipayConfig.host(), AlipayConfig.port(), AlipayConfig.path());
		this.protocol = Protocol.HTTPS;
	}
	
	@SuppressWarnings("unchecked")
	public static abstract class Builder<REQUEST, BUILDER extends Builder<REQUEST, BUILDER>> implements org.gatlin.core.service.http.HttpPost.Body {

		private static final long serialVersionUID = 7491685951687117463L;
		
//		@Expose
//		@SerializedName("alipay_sdk")
//		private String alipaySdk = "alipay-sdk-java-dynamicVersionNo";
		// 应用ID
		@Expose
		@SerializedName("app_id")
		protected String appIdd;
		// 接口名称
		@Expose
		protected String method;
		// 可选
		@Expose
		protected String format = "json";
		@Expose
		protected String charset = "utf-8";
		// 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
		@Expose
		@SerializedName("sign_type")
		protected String signType = "RSA2";
		@Expose
		protected String sign;
		// 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
		@Expose
		protected String timestamp = DateUtil.getDate(DateUtil.YYYY_MM_DD_HH_MM_SS);
		@Expose
		protected String version = "1.0";
		@Expose
		@SerializedName("notify_url")
		protected String notifyUrl = AlipayConfig.notifyUrlRecharge();
		@Expose
		@SerializedName("biz_content")
		protected String bizContent;
		
		public Builder(String metohd) {
			this.method = metohd;
			this.appIdd = AlipayConfig.appId();
		}
		
		public BUILDER notifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
			return (BUILDER) this;
		}
		
		public BUILDER timestamp(String timestamp) {
			this.timestamp = timestamp;
			return (BUILDER) this;
		}
		
		public abstract REQUEST build();
	}
}
