package org.gatlin.sdk.amazon.request;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.gatlin.core.bean.exceptions.RequestFailException;
import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.amazon.AmazonConfig;
import org.gatlin.sdk.amazon.AmazonUtil;
import org.gatlin.sdk.amazon.response.AmazonResponse;
import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.enums.Protocol;
import org.gatlin.util.serial.SerializeUtil;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("unchecked")
public class AmazonRequest<RESPONSE extends AmazonResponse, REQUEST extends AmazonRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public AmazonRequest(String path) {
		super(AmazonConfig.host(), 443, path);
		signatureVersion("2");
		signatureMethod("HmacSHA256");
		sellerId(AmazonConfig.sellerId());
		AWSAccessKeyId(AmazonConfig.AWSAccessKeyId());
		timestamp(DateUtil.iso8601UTCDate());
		this.protocol = Protocol.HTTPS;
	}
	
	// 亚马逊MWS账户访问密钥标识
	public REQUEST AWSAccessKeyId(String AWSAccessKeyId) {
		_addParam("AWSAccessKeyId", AWSAccessKeyId);
		return (REQUEST) this;
	}
	
	// 操作
	public REQUEST action(String action) {
		_addParam("Action", action);
		return (REQUEST) this;
	}
	
	// 标识亚马逊卖家对某个开发商的授权：仅适用于网页应用和第三方开发商授权
	public REQUEST MWSAuthToken(String MWSAuthToken) {
		_addParam("MWSAuthToken", MWSAuthToken);
		return (REQUEST) this;
	}
	
	// 卖家编号
	public REQUEST sellerId(String sellerId ) {
		_addParam("SellerId", sellerId );
		return (REQUEST) this;
	}
	
	// 卖家编号
	public REQUEST merchant(String merchant) {
		_addParam("Merchant", merchant);
		return (REQUEST) this;
	}
	
	public REQUEST signature(String signature) {
		_addParam("Signature", signature);
		return (REQUEST) this;
	}
	
	// 要用于计算签名的 HMAC 哈希算法。HmacSHA256 和 HmacSHA1 都支持哈希算法，但是亚马逊建议使用 HmacSHA256。
	public REQUEST signatureMethod(String signatureMethod) {
		_addParam("SignatureMethod", signatureMethod);
		return (REQUEST) this;
	}
	
	// 使用的签名版本。这是亚马逊 MWS 特定的信息，它告诉亚马逊 MWS 您使用哪种算法来生成构成签名基础的字符串。
	public REQUEST signatureVersion(String SignatureVersion) {
		_addParam("SignatureVersion", SignatureVersion);
		return (REQUEST) this;
	}
	
	// 当前的日期和时间或请求的到期日期和时间，格式为 ISO 8601。
	public REQUEST timestamp(String timestamp) {
		this.params.put("Timestamp", timestamp);
		return (REQUEST) this;
	}
	
	public REQUEST version(String version) {
		_addParam("Version", version);
		return (REQUEST) this;
	}
	
	@Override
	protected HttpUrl url() {
		HttpUrl.Builder builder = new HttpUrl.Builder().scheme(protocol.name());
		builder.host(host).port(port).addPathSegments(path);
		TreeMap<String, String> parameters = new TreeMap<String, String>(this.params);
		String strToSign = AmazonUtil.stringToSign(parameters);
		String signature = AmazonUtil.sign(strToSign);
		parameters.put("Signature", AmazonUtil.urlEncode(signature));
		for (Entry<String, String> entry : parameters.entrySet())
			builder.addQueryParameter(entry.getKey(), entry.getValue());
		System.out.println(builder.build());
		return builder.build();
	}
	
	@Override
	protected Request request() {
		Request.Builder rb = new Request.Builder().url(url());
		for (Entry<String, String> entry : headers.entrySet())
			rb.addHeader(entry.getKey(), entry.getValue());
		return rb.build();
	}
	
	protected RESPONSE response(Response response) {
		try {
			String body = response.body().string();
			System.out.println(body);
			RESPONSE resp = SerializeUtil.XmlUtil.xmlToBean(body, clazz);
			resp.verify();
			return resp;
		} catch (IOException e) {
			throw new RequestFailException(e);
		}
	}
	
	protected REQUEST _addParam(String key, String value) {
		this.params.put(key, AmazonUtil.urlEncode(value));
		return (REQUEST) this;
	}
	
	protected REQUEST _addParams(Map<String, String> params) {
		params.entrySet().forEach(item -> _addParam(item.getKey(), AmazonUtil.urlEncode(item.getValue())));
		return (REQUEST) this;
	}
}
