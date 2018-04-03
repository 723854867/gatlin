package org.gatlin.soa.courier.api;

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
	boolean captchaVerify(String mobile, String captcha);
}
