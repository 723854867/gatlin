package org.gatlin.dao.mongo;

import javax.annotation.Resource;

import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.dao.DaoTest;
import org.gatlin.dao.mongo.dao.LogRequestDao;
import org.junit.Test;

public class LogRequestTest extends DaoTest {

	@Resource
	private LogRequestDao logRequestDao;
	
	@Test
	public void testInsert() {
		LogRequest request = new LogRequest();
		request.set_id("xxxx");
		logRequestDao.insert(request);
	}
}
