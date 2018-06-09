package org.gatlin.soa.courier.api;

import java.util.Set;

public interface EmailService {

	/**
	 * 获取验证码
	 */
	String captchaAcquire(String email);
	
	/**
	 * 验证码校验
	 */
	void captchaVerify(String email, String captcha);
	
	/**
	 * 发送html邮件
	 * 
	 * @param content
	 * @param title
	 * @param acceptors
	 */
	void sendHtml(String content, String title, Set<String> acceptors);
}
