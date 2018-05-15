package org.gatlin.sdk.sinapay.notice;

import org.gatlin.core.bean.model.message.Notice;

public class SinaNotice extends Notice {

	private static final long serialVersionUID = 1782908032182804981L;

	public static final String RESPONSE_OK = "success";

	// 通知类型
	private String notify_type;
	// 通知编号
	private String notify_id;
	// 参数编码字符集:注意真实字符串为 input_charset
	private String _input_charset;
	// 通知时间
	private String notify_time;
	// 签名
	private String sign;
	// 签名方式
	private String sign_type;
	// 接口版本号
	private String version;
	// 备注
	private String memo;
	// 返回错误码
	private String error_code;
	// 返回错误原因
	private String error_message;

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String get_input_charset() {
		return _input_charset;
	}
	
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public boolean success() {
		return this.error_code.equalsIgnoreCase("success");
	}

	@Override
	public void verify() {
		super.verify();
	}
}
