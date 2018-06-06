package org.gatlin.soa.courier.api;

import java.util.Collection;

/**
 * 短信服务接口
 * 
 * @author lynn
 */
public interface SmsService {

	/**
	 * 获取验证码
	 */
	String captchaAcquire(String mobile);
	
	/**
	 * 验证码校验
	 */
	void captchaVerify(String mobile, String captcha);
	
	/**
	 * 群发短信
	 */
	void send(String content, Collection<String> mobiles);
}
