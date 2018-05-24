package org.gatlin.sdk.jisu;

import org.gatlin.core.Bootstrap;
import org.gatlin.sdk.jisu.request.JieQiRequest;
import org.gatlin.sdk.jisu.result.JieQi;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class JisuTest {

	@Test
	public void test() {} 
	
	@Test
	public void testJieQi() {
		JieQiRequest request = new JieQiRequest();
		JisuResponse<JieQi> response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
