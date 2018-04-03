package org.gatlin.soa.log.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.entity.LogRequest;
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
}
