package org.gatlin.web.bean.param;

import java.util.Set;

import org.gatlin.soa.bean.param.SoaParam;

public class CfgResourceSearcher extends SoaParam {

	private static final long serialVersionUID = -2616953281171089490L;
	private Set<Integer> ids;
	
	@Override
	public void verify() {
		super.verify();
		if (null != ids && ids.size()>0)
			this.query.in("id", ids);
		this.query.orderByAsc("id");
	}
}
