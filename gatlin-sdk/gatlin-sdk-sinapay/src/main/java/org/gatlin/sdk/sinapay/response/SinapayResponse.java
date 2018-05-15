package org.gatlin.sdk.sinapay.response;

import org.gatlin.core.service.http.HttpResponse;
import org.gatlin.sdk.sinapay.bean.SinapayException;

import com.google.gson.annotations.SerializedName;

public class SinapayResponse implements HttpResponse {

	private static final long serialVersionUID = 2284939491804409446L;
	
	@SerializedName("response_time")
	private String responseTime;
	@SerializedName("partner_id")
	private String partnerId;
	@SerializedName("_input_charset")
	private String inputCharset;
	private String sign;
	@SerializedName("sign_type")
	private String signType;
	@SerializedName("sign_version")
	private String signVersion;
	@SerializedName("response_code")
	private String responseCode;
	@SerializedName("response_message")
	private String responseMessage;
	// 与request中的memo一致，可以理解为透传参数
	private String memo;
	@SerializedName("error_url")
	private String errorUrl;

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignVersion() {
		return signVersion;
	}

	public void setSignVersion(String signVersion) {
		this.signVersion = signVersion;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	@Override
	public String code() {
		return this.responseCode;
	}

	@Override
	public String desc() {
		return this.responseMessage;
	}

	@Override
	public void verify() {
		if (!this.responseCode.equalsIgnoreCase("APPLY_SUCCESS"))
			throw new SinapayException(responseCode, responseMessage);
	}
}
