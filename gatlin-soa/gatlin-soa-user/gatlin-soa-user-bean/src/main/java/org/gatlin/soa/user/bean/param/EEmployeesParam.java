package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaLidParam;

public class EEmployeesParam extends SoaLidParam {

	private static final long serialVersionUID = -1860389065422961687L;

	private String name;
	private String mobile;
	private String identity;
	private Long employeeId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
