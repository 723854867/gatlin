package org.gatlin.soa.courier.manager;

import java.util.Collection;

import javax.annotation.Resource;

import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.courier.CourierConsts;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.lang.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsRouteManager {

	@Resource
	private ConfigService configService;
	@Autowired(required = false)
	private ChuangLanManager chuangLanManager;
	
	public void send(String content, String mobile) { 
		PlatType plat = EnumUtil.valueOf(PlatType.class, configService.config(CourierConsts.SMS_PLAT));
		switch (plat) {
		case CHUANGLAN:
			if (null != chuangLanManager)
				chuangLanManager.sendSms(content, String.valueOf(PhoneUtil.getNationalNumber(mobile)));
			break;
		default:
			break;
		}
	}
	
	public void send(String content, Collection<String> mobiles) { 
		PlatType plat = EnumUtil.valueOf(PlatType.class, configService.config(CourierConsts.SMS_PLAT));
		switch (plat) {
		case CHUANGLAN:
			if (null != chuangLanManager)
				chuangLanManager.sendSms(content, mobiles);
			break;
		default:
			break;
		}
	}
}
