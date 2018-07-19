package org.gatlin.core;

import javax.annotation.Resource;

import org.gatlin.core.service.http.HttpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class CoreTest {
	
	@Resource
	private HttpService httpService;

	@Test
	public void test() {}
	
}
