package org.gatlin.soa.bean.param;

import javax.validation.constraints.Min;

public class SoaIdParam extends SoaParam {

	private static final long serialVersionUID = -1191533546309903561L;

	@Min(1)
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
