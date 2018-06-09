package org.gatlin.soa.courier.service;

import java.util.Set;

import javax.annotation.Resource;

import org.gatlin.core.service.mail.MailSender;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.courier.CaptchaType;
import org.gatlin.soa.courier.api.EmailService;
import org.gatlin.soa.courier.bean.CourierCode;
import org.gatlin.soa.courier.manager.CourierManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired(required = false)
	private MailSender mailSender;
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
	
	@Override
	public void sendHtml(String content, String title, Set<String> acceptors) {
		Assert.notNull(CourierCode.MAIL_DISABLE, mailSender);
		mailSender.sendHtmlMail(acceptors, title, content);
	}
}
