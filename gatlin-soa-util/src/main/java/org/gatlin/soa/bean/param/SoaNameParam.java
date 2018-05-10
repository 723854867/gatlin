package org.gatlin.soa.bean.param;

import javax.validation.constraints.NotEmpty;

public class SoaNameParam extends SoaParam {

	private static final long serialVersionUID = 5744556147839174645L;

	@NotEmpty
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
