package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.StringUtil;

public class CompaniesParam extends SoaParam {

	private static final long serialVersionUID = 2405436543851873264L;

	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != id)
			this.query.eq("id", id);
		if (StringUtil.hasText(name))
			this.query.like("name", name);
	}
}
