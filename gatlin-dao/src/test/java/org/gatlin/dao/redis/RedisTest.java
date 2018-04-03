package org.gatlin.dao.redis;

import javax.annotation.Resource;

import org.gatlin.dao.DaoTest;
import org.junit.Test;

public class RedisTest extends DaoTest {

	@Resource
	private Redis redis;
	
	@Test
	public void testGet() {
		redis.get("sdsd");
	}
	
	@Test
	public void testCaptchaObtain() {
		long result = redis.captchaObtain("sss", "sssss", "1234", 60000, 10, 60000000, 60000);
		System.out.println(result);
	}
}
