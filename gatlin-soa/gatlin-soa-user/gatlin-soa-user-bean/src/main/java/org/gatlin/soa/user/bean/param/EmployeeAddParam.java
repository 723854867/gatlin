package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.soa.bean.param.SoaIdParam;

public class EmployeeAddParam extends SoaIdParam {

	private static final long serialVersionUID = 8986084962928737720L;

	@Min(1)
	private long uid;
	
	public long getUid() {
		return uid;
	}
	
	public void setUid(long uid) {
		this.uid = uid;
	}
}
