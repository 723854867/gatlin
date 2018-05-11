package org.gatlin.core.bean.model.message;

import org.gatlin.core.bean.entity.LogRequest;

/**
 * 通知
 * 
 * @author lynn
 */
public class Notice implements Request {

	private static final long serialVersionUID = -1920657361787296335L;
	
	private LogRequest meta;
	
	@Override
	public void dispose() {
		this.meta = null;
	}
	
	public LogRequest meta() {
		return meta;
	}

	@Override
	public void init(LogRequest meta, Object... attaches) {
		this.meta = meta;
	}
}
