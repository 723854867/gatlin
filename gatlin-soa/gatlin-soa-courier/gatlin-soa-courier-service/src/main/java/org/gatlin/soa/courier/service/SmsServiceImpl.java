package org.gatlin.soa.courier.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.soa.courier.CaptchaType;
import org.gatlin.soa.courier.api.SmsService;
import org.gatlin.soa.courier.bean.CourierCode;
import org.gatlin.soa.courier.manager.CourierManager;
import org.gatlin.soa.courier.manager.SmsRouteManager;
import org.gatlin.util.PhoneUtil;
import org.springframework.stereotype.Service;

@Service("smsService")
public class SmsServiceImpl implements SmsService {
	
	@Resource
	private CourierManager courierManager;
	@Resource
	private SmsRouteManager smsRouteManager;
	
	@Override
	public String captchaAcquire(String mobile) {
		return courierManager.captchaAcquire(CaptchaType.MOBILE, PhoneUtil.parseMobile(mobile));
	}

	@Override
	public void captchaVerify(String mobile, String captcha) {
		boolean pass = courierManager.captchaVerify(CaptchaType.MOBILE, PhoneUtil.parseMobile(mobile), captcha);
		Assert.isTrue(CourierCode.CAPTCHA_ERR, pass);
	}
	
	@Override
	public void send(String content, Collection<String> mobiles) {
		smsRouteManager.send(content, mobiles);
	}
}
