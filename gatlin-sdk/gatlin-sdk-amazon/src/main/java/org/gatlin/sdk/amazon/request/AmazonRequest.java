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
import org.gatlin.sdk.amazon.response.ErrorResponse;
import org.gatlin.util.Consts;
import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.enums.Protocol;
import org.gatlin.util.serial.SerializeUtil;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings("unchecked")
public class AmazonRequest<RESPONSE extends AmazonResponse, REQUEST extends AmazonRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public AmazonRequest(String action, String version, String path) {
		super(AmazonConfig.host(), 443, path);
		action(action);
		version(version);
		signatureVersion("2");
		signatureMethod("HmacSHA256");
		AWSAccessKeyId(AmazonConfig.AWSAccessKeyId());
		timestamp(DateUtil.iso8601UTCMillisDate());
		this.protocol = Protocol.HTTPS;
	}
	
	// 亚马逊MWS账户访问密钥标识
	public REQUEST AWSAccessKeyId(String AWSAccessKeyId) {
		return _addParam("AWSAccessKeyId", AWSAccessKeyId);
	}
	
	// 操作
	public REQUEST action(String action) {
		return _addParam("Action", action);
	}
	
	// 标识亚马逊卖家对某个开发商的授权：仅适用于网页应用和第三方开发商授权
	public REQUEST MWSAuthToken(String MWSAuthToken) {
		return _addParam("MWSAuthToken", MWSAuthToken);
	}
	
	public REQUEST signature(String signature) {
		return _addParam("Signature", signature);
	}
	
	// 要用于计算签名的 HMAC 哈希算法。HmacSHA256 和 HmacSHA1 都支持哈希算法，但是亚马逊建议使用 HmacSHA256。
	public REQUEST signatureMethod(String signatureMethod) {
		_addParam("SignatureMethod", signatureMethod);
		return (REQUEST) this;
	}
	
	// 使用的签名版本。这是亚马逊 MWS 特定的信息，它告诉亚马逊 MWS 您使用哪种算法来生成构成签名基础的字符串。
	public REQUEST signatureVersion(String SignatureVersion) {
		return _addParam("SignatureVersion", SignatureVersion);
	}
	
	// 当前的日期和时间或请求的到期日期和时间，格式为 ISO 8601。
	public REQUEST timestamp(String timestamp) {
		return _addParam("Timestamp", timestamp);
	}
	
	public REQUEST version(String version) {
		return _addParam("Version", version);
	}
	
	@Override
	protected HttpUrl url() {
		HttpUrl.Builder builder = new HttpUrl.Builder().scheme(protocol.name());
		builder.host(host).port(port).addPathSegments(path);
		TreeMap<String, String> parameters = new TreeMap<String, String>(this.params);
		String strToSign = AmazonUtil.stringToSign(path, parameters);
		String signature = AmazonUtil.sign(strToSign);
		this.params.put("Signature", signature);
		parameters.put("Signature", signature);
		return builder.build();
	}
	
	@Override
	protected RequestBody requestBody() {
		FormBody.Builder fb = new FormBody.Builder(Consts.UTF_8);
		for (Entry<String, String> entry : params.entrySet())
			fb.addEncoded(AmazonUtil.urlEncode(entry.getKey()), AmazonUtil.urlEncode(entry.getValue()));
		return fb.build();
	}
	
	@Override
	protected void requestFailure(Response response) {
		String errorContent = null;
		try {
			errorContent = response.body().string();
		} catch (Exception e) {
			super.requestFailure(response);
			return;
		}
		ErrorResponse err = SerializeUtil.XmlUtil.xmlToBean(errorContent, ErrorResponse.class);
		err.verify();
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
		this.params.put(key, value);
		return (REQUEST) this;
	}
	
	protected REQUEST _addParams(Map<String, String> params) {
		this.params.putAll(params);
		return (REQUEST) this;
	}
}
