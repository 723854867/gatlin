package org.gatlin.web.bean.param;

import java.util.Set;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.CollectionUtil;

public class CfgResourceListParam extends SoaParam {

	private static final long serialVersionUID = -2616953281171089490L;
	
	private Set<Integer> ids;
	
	@Override
	public void verify() {
		super.verify();
		if (!CollectionUtil.isEmpty(ids))
			this.query.in("id", ids);
		this.query.orderByAsc("id");
	}
}
