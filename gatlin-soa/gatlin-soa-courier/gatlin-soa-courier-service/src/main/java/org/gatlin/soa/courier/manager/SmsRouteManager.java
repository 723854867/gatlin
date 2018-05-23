package org.gatlin.soa.courier.manager;

import javax.annotation.Resource;

import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.courier.CourierConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsRouteManager {

	@Resource
	private ConfigService configService;
	@Autowired(required = false)
	private ChuangLanManager chuangLanManager;
	
	public void send(String content, String mobile) { 
		PlatType plat = configService.config(CourierConsts.SMS_PLAT);
		switch (plat) {
		case CHUANGLAN:
			if (null != chuangLanManager)
				chuangLanManager.sendSms(content, mobile);
			break;
		default:
			break;
		}
	}
}
