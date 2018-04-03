package org.gatlin.dao.mongo.dao;

import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.dao.mongo.MongoCondition;
import org.gatlin.dao.mongo.MongoDao;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(MongoCondition.class)
public class LogRequestDao extends MongoDao<String, LogRequest> {

	public LogRequestDao() {
		super("log_request");
	}
}
