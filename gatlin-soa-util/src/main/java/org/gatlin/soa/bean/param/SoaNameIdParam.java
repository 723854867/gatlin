package org.gatlin.soa.bean.param;

import javax.validation.constraints.NotEmpty;

public class SoaNameIdParam extends SoaIdParam {

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
