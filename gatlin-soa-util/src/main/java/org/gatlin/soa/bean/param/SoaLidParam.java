package org.gatlin.soa.bean.param;

import javax.validation.constraints.Min;

public class SoaLidParam extends SoaParam {

	private static final long serialVersionUID = -267953825779739930L;

	@Min(1)
	private long id;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}
