package org.gatlin.core.bean.param;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.entity.LogRequest;
import org.gatlin.core.bean.model.message.Request;
import org.gatlin.core.util.Assert;

public class Param implements Request {

	private static final long serialVersionUID = -5045424390642966673L;
	
	private Integer page;
	private LogRequest meta;
	private Integer pageSize;
	
	@Override
	public void dispose() {
		this.meta = null;
	}
	
	public LogRequest meta() {
		return meta;
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void init(LogRequest meta, Object... attaches) {
		this.meta = meta;
	}
	
	@Override
	public void verify() {
		Request.super.verify();
		if (null != page)
			Assert.notNull(CoreCode.PARAM_ERR, pageSize);
	}
}
