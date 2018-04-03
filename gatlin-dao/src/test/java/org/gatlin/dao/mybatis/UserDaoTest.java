package org.gatlin.dao.mybatis;

import javax.annotation.Resource;

import org.gatlin.dao.DaoTest;
import org.gatlin.dao.bean.entity.User;
import org.gatlin.dao.mybatis.dao.UserDao;
import org.junit.Test;

public class UserDaoTest extends DaoTest {

	@Resource
	private Tx tx;
	@Resource
	private UserDao userDao;
	
	@Test
	public void testGet() {
		User user = userDao.getByKey(1l);
		System.out.println(user);
		System.out.println(user.getLoginName() + " " + user.getAge());
	}
	
	@Test
	public void testInsertTx() throws InterruptedException { 
		User user = new User();
		user.setAge(10);
		user.setLoginName("sdsds");
		tx.insert(user);
	}
}
