package org.gatlin.sdk.alipay.response;

import org.gatlin.core.service.http.HttpResponse;
import org.gatlin.sdk.alipay.bean.AlipayException;
import org.gatlin.sdk.alipay.bean.enums.Code;

import com.google.gson.annotations.SerializedName;

public class AlipayResponse implements HttpResponse {

	private static final long serialVersionUID = -9006133863002341110L;

	private String code;
	private String msg;
	@SerializedName("sub_code")
	private String subCode;
	@SerializedName("sub_msg")
	private String subMsg;
	private String sign;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String code() {
		return this.code;
	}

	@Override
	public String desc() {
		return this.msg;
	}

	@Override
	public void verify() {
		Code code = Code.match(this.code);
		if (code == Code.SUCCESS)
			return;
		throw new AlipayException(this.code, msg);
	}
}
