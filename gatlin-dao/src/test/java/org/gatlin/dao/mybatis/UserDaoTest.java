package org.gatlin.dao.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gatlin.dao.DaoTest;
import org.gatlin.dao.bean.entity.LogSms;
import org.gatlin.dao.bean.entity.User;
import org.gatlin.dao.mybatis.dao.LogSmsDao;
import org.gatlin.dao.mybatis.dao.UserDao;
import org.junit.Test;

public class UserDaoTest extends DaoTest {

	@Resource
	private Tx tx;
	@Resource
	private UserDao userDao;
	@Resource
	private LogSmsDao logSmsDao;
	
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
	
	@Test
	public void testBatchInsert() {
		List<LogSms> list = new ArrayList<LogSms>();
		LogSms log = new LogSms();
		log.setContent("sd");
		log.setCreated(0);
		log.setUpdated(0);
		log.setMobile("sdsd");
		log.setMsgId("sss");
		log.setSendTime("");
		log.setState(1);
		list.add(log);
		log = new LogSms();
		log.setContent("sd");
		log.setCreated(0);
		log.setUpdated(0);
		log.setMobile("sdsd");
		log.setMsgId("sss");
		log.setSendTime("");
		log.setState(1);
		list.add(log);
		logSmsDao.batchInsert(list);
		
	}
}
