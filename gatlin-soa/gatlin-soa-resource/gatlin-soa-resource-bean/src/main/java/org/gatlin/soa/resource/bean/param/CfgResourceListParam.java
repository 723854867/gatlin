package org.gatlin.soa.resource.bean.param;

import java.util.Set;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.CollectionUtil;

public class CfgResourceListParam extends SoaParam {

	private static final long serialVersionUID = -2616953281171089490L;
	
	private Integer type;
	private Set<Integer> ids;
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Set<Integer> getIds() {
		return ids;
	}
	
	public void setIds(Set<Integer> ids) {
		this.ids = ids;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != type)
			this.query.eq("type", type);
		if (!CollectionUtil.isEmpty(ids))
			this.query.in("id", ids);
		this.query.orderByAsc("id");
	}
}
