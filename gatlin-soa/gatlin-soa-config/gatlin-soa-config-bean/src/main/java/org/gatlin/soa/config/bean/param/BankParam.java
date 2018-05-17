package org.gatlin.soa.config.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaSidParam;

public class BankParam extends SoaSidParam {

	private static final long serialVersionUID = -5160430708484423950L;

	@NotEmpty
	private String name;
	private boolean valid;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
