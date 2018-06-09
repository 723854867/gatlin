package org.gatlin.soa.log.service;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.dao.mongo.dao.LogRequestDao;
import org.gatlin.soa.log.api.LogService;
import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogRequestDao logRequestDao;

	@Override
	public void logRequest(LogRequest log) {
		logRequestDao.insert(log);
	}
	
	@Override
	public Pager<LogRequest> requests(Query query) {
		Assert.notNull(CoreCode.PARAM_ERR, query.getPage(), query.getPageSize());
		return new Pager<LogRequest>(logRequestDao.queryList(query));
	}
}
