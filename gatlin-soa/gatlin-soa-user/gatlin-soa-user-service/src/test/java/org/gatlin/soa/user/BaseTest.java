package org.gatlin.soa.user;

import javax.annotation.Resource;

import org.gatlin.dao.mybatis.dao.UserDao;
import org.gatlin.soa.user.mybatis.dao.EmployeeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ComponentScan("org.gatlin")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseTest.class)
public class BaseTest {
	
	@Resource
	private UserDao userDao;
	@Resource
	private EmployeeDao employeeDao;
	
	@Test
	public void test() {}

	@Test
	public void testQuery() {
		
	}
}
