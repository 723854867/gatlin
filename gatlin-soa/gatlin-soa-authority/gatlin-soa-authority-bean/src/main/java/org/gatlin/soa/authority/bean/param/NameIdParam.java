package org.gatlin.soa.authority.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaIdParam;

public class NameIdParam extends SoaIdParam {

	private static final long serialVersionUID = -2697177843839151725L;

	@NotEmpty
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
