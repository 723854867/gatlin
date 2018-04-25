package org.gatlin.sdk.ucpaas.model;

public enum SmsType {

	// 通知短信
	NOTICE("0"),
	// 会员服务短信(需要企业认证)
	MEMBER("5"),
	// 验证码短信
	CAPTCHA("4");
	
	private String mark;
	
	private SmsType(String mark) {
		this.mark = mark;
	}
	
	public String mark() {
		return mark;
	}
}
