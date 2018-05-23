package org.gatlin.soa.courier.manager;

import org.gatlin.skd.chuanglan.request.SmsRequest;
import org.gatlin.soa.courier.ChuangLanCondition;
import org.gatlin.util.PhoneUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(ChuangLanCondition.class)
public class ChuangLanManager {

	public void sendSms(String content, String mobile) {
		SmsRequest.Builder builder = new SmsRequest.Builder();
		builder.msg(content);
		builder.phone(String.valueOf(PhoneUtil.getNationalNumber(mobile)));
		SmsRequest request = builder.build();
		request.sync();
	}
}
