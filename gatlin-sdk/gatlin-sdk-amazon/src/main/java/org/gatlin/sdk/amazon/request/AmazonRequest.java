package org.gatlin.sdk.amazon.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.core.service.http.HttpResponse;

@SuppressWarnings("unchecked")
public class AmazonRequest<REQUEST extends AmazonRequest<REQUEST>> extends HttpPost<HttpResponse, REQUEST> {

	public AmazonRequest(String host, int port, String path) {
		super(host, port, path);
	}
	
	// 亚马逊MWS账户访问密钥标识
	public REQUEST AWSAccessKeyId(String AWSAccessKeyId) {
		this.params.put("AWSAccessKeyId", AWSAccessKeyId);
		return (REQUEST) this;
	}
	
	// 操作
	public REQUEST action(String action) {
		this.params.put("Action", action);
		return (REQUEST) this;
	}
	
	// 标识亚马逊卖家对某个开发商的授权：仅适用于网页应用和第三方开发商授权
	public REQUEST MWSAuthToken(String MWSAuthToken) {
		this.params.put("MWSAuthToken", MWSAuthToken);
		return (REQUEST) this;
	}
	
	// 卖家编号
	public REQUEST sellerId(String sellerId ) {
		this.params.put("SellerId ", sellerId );
		return (REQUEST) this;
	}
	
	// 卖家编号
	public REQUEST merchant(String merchant) {
		this.params.put("Merchant ", merchant);
		return (REQUEST) this;
	}
	
	public REQUEST signature(String signature) {
		this.params.put("Signature ", signature);
		return (REQUEST) this;
	}
	
	// 要用于计算签名的 HMAC 哈希算法。HmacSHA256 和 HmacSHA1 都支持哈希算法，但是亚马逊建议使用 HmacSHA256。
	public REQUEST signatureMethod(String signatureMethod) {
		this.params.put("SignatureMethod ", signatureMethod);
		return (REQUEST) this;
	}
	
	// 使用的签名版本。这是亚马逊 MWS 特定的信息，它告诉亚马逊 MWS 您使用哪种算法来生成构成签名基础的字符串。
	public REQUEST signatureVersion(String SignatureVersion) {
		this.params.put("SignatureVersion ", SignatureVersion);
		return (REQUEST) this;
	}
	
	// 每个请求都必须包含请求的时间戳。根据您使用的 API 操作，您可以提供该请求的到期日期和时间，而不是时间戳。格式为 ISO 8601。
	public REQUEST timestamp(String timestamp) {
		this.params.put("Timestamp ", timestamp);
		return (REQUEST) this;
	}
	
	public REQUEST version(String version) {
		this.params.put("Version ", version);
		return (REQUEST) this;
	}
}
