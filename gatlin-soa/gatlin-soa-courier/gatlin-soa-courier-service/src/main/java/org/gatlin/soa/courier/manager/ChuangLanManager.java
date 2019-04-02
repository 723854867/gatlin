package org.gatlin.soa.courier.manager;

import java.util.Collection;
import java.util.Iterator;

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
		builder.phone(mobile);
		SmsRequest request = builder.build();
		request.sync();
	}
	
	public void sendSms(String content, Collection<String> mobiles) {
		Iterator<String> iterator = mobiles.iterator();
		StringBuilder builder = new StringBuilder();
		int idx = 0;
		while (iterator.hasNext()) {
			builder.append(PhoneUtil.getNationalNumber(iterator.next())).append(",");
			idx++;
			if (idx == 500) {
				builder.deleteCharAt(builder.length() - 1);
				idx = 0;
				sendSms(content, builder.toString());
				builder = new StringBuilder();
			}
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
			sendSms(content,  builder.toString());
		}
	}
}
