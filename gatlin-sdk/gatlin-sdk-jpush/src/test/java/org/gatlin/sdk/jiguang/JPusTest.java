package org.gatlin.sdk.jiguang;

import org.gatlin.core.Bootstrap;
import org.gatlin.sdk.jpush.bean.model.Message;
import org.gatlin.sdk.jpush.bean.model.PushBody;
import org.gatlin.sdk.jpush.request.Jpush;
import org.gatlin.sdk.jpush.response.JPushResponse;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Bootstrap.class })
public class JPusTest {

	@Test
	public void test() {
	}

	@Test
	public void testPush() {
		PushBody body = new PushBody();
		body.platformAll();
		body.audienceAll();
		body.message( new Message().content("hello world programar"));
		Jpush jpush = new Jpush(body);
		System.out.println(SerializeUtil.GSON.toJson(body));
		JPushResponse response = jpush.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
