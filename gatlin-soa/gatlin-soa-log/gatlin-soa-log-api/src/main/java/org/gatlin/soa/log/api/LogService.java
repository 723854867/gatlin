package org.gatlin.soa.log.api;

import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;

public interface LogService {
	
	// 记录访问日志
	void logRequest(LogRequest log);
	
	Pager<LogRequest> requests(Query query);
}
