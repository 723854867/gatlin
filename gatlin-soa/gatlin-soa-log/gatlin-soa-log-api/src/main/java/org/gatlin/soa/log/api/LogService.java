package org.gatlin.soa.log.api;

import org.gatlin.core.bean.entity.LogRequest;

public interface LogService {
	
	// 记录访问日志
	void logRequest(LogRequest log);
}
