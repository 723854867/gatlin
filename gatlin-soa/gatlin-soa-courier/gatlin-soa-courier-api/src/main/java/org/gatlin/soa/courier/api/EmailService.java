package org.gatlin.soa.courier.api;

public interface EmailService {

	/**
	 * 获取验证码
	 */
	String captchaAcquire(String email);
	
	/**
	 * 验证码校验
	 */
	boolean captchaVerify(String email, String captcha);
}
