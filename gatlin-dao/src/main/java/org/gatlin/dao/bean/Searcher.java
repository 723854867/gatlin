package org.gatlin.dao.bean;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.param.Param;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;

public class Searcher extends Param {

	private static final long serialVersionUID = -2893191744676655636L;

	protected Query query;
	
	public Query query() {
		return query;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != getPage())
			Assert.notNull(CoreCode.PARAM_ERR, getPageSize());
		if (null == query)
			this.query = new Query();
		if (null != getPage())
			this.query.page(getPage());
		if (null != getPageSize())
			this.query.pageSize(getPageSize());
	}
}
