package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.user.bean.enums.EmployeeState;

public class EmployeeModifyParam extends SoaLidParam {

	private static final long serialVersionUID = -3745776933633448687L;

	private EmployeeState state;
	
	public EmployeeState getState() {
		return state;
	}
	
	public void setState(EmployeeState state) {
		this.state = state;
	}
}
