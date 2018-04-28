package org.gatlin.soa.resource.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaLidParam;

public class ResourceModifyParam extends SoaLidParam {

	private static final long serialVersionUID = 1979697622927062997L;

	@NotEmpty
	private String name;
	private int priority;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
