package org.gatlin.sdk.jiguang;

import org.gatlin.core.Bootstrap;
import org.gatlin.sdk.jiguang.model.PushBody;
import org.gatlin.sdk.jiguang.request.Jpush;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class JiGuangTest {

	@Test
	public void test() {}
	
	@Test
	public void testVerify() {
		PushBody body = new PushBody();
		Jpush push = new Jpush(body);
		push.sync();
	}
}
