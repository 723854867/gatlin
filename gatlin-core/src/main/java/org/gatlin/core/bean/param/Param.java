package org.gatlin.core.bean.param;

import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.core.bean.model.message.Request;

public class Param implements Request {

	private static final long serialVersionUID = -5045424390642966673L;
	
	private LogRequest meta;

	@Override
	public void dispose() {
		this.meta = null;
	}

	@Override
	public void init(LogRequest meta, Object... attaches) {
		this.meta = meta;
	}
}
