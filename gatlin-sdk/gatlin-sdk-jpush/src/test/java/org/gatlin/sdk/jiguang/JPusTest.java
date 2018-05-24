package org.gatlin.sdk.jiguang;

import java.util.HashMap;
import java.util.Map;

import org.gatlin.core.Bootstrap;
import org.gatlin.sdk.jiguang.model.Message;
import org.gatlin.sdk.jiguang.model.Options;
import org.gatlin.sdk.jiguang.model.Platform;
import org.gatlin.sdk.jiguang.model.PushBody;
import org.gatlin.sdk.jiguang.request.Jpush;
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
		body.platform(Platform.android, Platform.ios);
		body.audienceAll();
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("from", "JPush");
		body.message( new Message().content("hello world").extras(extras));
		body.options(new Options().apnsProduction(false).sendno(1150991922));
		Jpush jpush = new Jpush(body);
		jpush.sync();
	}
}
