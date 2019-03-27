package org.gatlin.sdk.heepay.response;

import org.gatlin.core.service.http.HttpResponse;
import org.gatlin.sdk.heepay.bean.HeepayException;
import org.gatlin.sdk.heepay.bean.enums.Code;

import com.google.gson.annotations.SerializedName;

public class BatchPayResponse implements HttpResponse {

	private static final long serialVersionUID = 4382362798210473591L;
	
	@SerializedName("ret_code")
	private String code;
	@SerializedName("ret_msg")
	private String msg;
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

	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	@Override
	public void verify() {
		Code code = Code.match(this.code);
		if (code == Code.PAY_SUCCESS)
			return;
//		throw new HeepayException(this.code, this.msg);
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public String desc() {
		return msg;
	}
}
