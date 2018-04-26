package org.gatlin.soa.courier.service;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.soa.courier.CaptchaType;
import org.gatlin.soa.courier.api.EmailService;
import org.gatlin.soa.courier.bean.CourierCode;
import org.gatlin.soa.courier.manager.CourierManager;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Resource
	private CourierManager courierManager;
	
	@Override
	public String captchaAcquire(String email) {
		return courierManager.captchaAcquire(CaptchaType.EMAIL, email);
	}

	@Override
	public void captchaVerify(String email, String captcha) {
		boolean pass = courierManager.captchaVerify(CaptchaType.EMAIL, email, captcha);
		Assert.isTrue(CourierCode.CAPTCHA_ERR, pass);
	}
}
