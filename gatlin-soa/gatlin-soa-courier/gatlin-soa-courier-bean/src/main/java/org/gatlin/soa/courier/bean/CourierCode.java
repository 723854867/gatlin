package org.gatlin.soa.courier.bean;

import org.gatlin.core.bean.model.code.Code;

public interface CourierCode {
	
	final Code CAPTCHA_ERR 							= new Code("code.captcha.err", "验证码错误");
	final Code CAPTCHA_OBTAIN_FREQ 					= new Code("code.captcha.obtain.freq", "验证码获取太频繁");
	final Code CAPTCHA_OBTAIN_COUNT_LIMIT 			= new Code("code.captcha.obtain.count.limit", "验证码获取次数限制");
}
