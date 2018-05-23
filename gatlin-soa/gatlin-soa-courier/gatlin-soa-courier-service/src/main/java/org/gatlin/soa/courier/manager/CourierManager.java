package org.gatlin.soa.courier.manager;

import javax.annotation.Resource;

import org.gatlin.core.Gatlin;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.dao.redis.Redis;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.courier.CaptchaType;
import org.gatlin.soa.courier.CourierConsts;
import org.gatlin.soa.courier.KeyGenerator;
import org.gatlin.soa.courier.bean.CourierCode;
import org.gatlin.util.KeyUtil;
import org.springframework.stereotype.Component;

@Component
public class CourierManager {
	
	@Resource
	private Redis redis;
	@Resource
	private Gatlin gatlin;
	@Resource
	private ConfigService configService;
	@Resource
	private SmsRouteManager smsRouteManager;

	public String captchaAcquire(CaptchaType type, String receiver) {
		Configs configs = configService.configs(new Query());
		String captcha = KeyUtil.randomCode(configs.get(CourierConsts.CAPTCHA_BIT_NUM), true);
		String key = KeyGenerator.captchaKey(type, receiver);
		String countKey = KeyGenerator.captchaCountKey(type, receiver);
		int lifeTime = configs.get(CourierConsts.CAPTCHA_LIFE_TIME);
		int countMaximum = configs.get(CourierConsts.CAPTCHA_COUNT_MAXIMUM);
		int countLifeTime = configs.get(CourierConsts.CAPTCHA_COUNT_LIFE_TIME);
		int interval = configs.get(CourierConsts.CAPTCHA_COUNT_LIFE_TIME);
		long flag = redis.captchaObtain(key, countKey, captcha, lifeTime, countMaximum, countLifeTime, interval);
		if (flag == 1)
			throw new CodeException(CourierCode.CAPTCHA_OBTAIN_FREQ);
		if (flag == 2)
			throw new CodeException(CourierCode.CAPTCHA_OBTAIN_COUNT_LIMIT);
		boolean smsEnable = configService.config(CourierConsts.SMS_ENABLE);
		if (!smsEnable) 			
			return captcha;
		switch (type) {
		case MOBILE:
			smsRouteManager.send("", receiver);
		case EMAIL:
			break;
		default:
			throw new CodeException();
		}
		return null;
	}

	public boolean captchaVerify(CaptchaType type, String receiver, String captcha) {
		return redis.delIfEquals(KeyGenerator.captchaKey(type, receiver), captcha);
	}
}
