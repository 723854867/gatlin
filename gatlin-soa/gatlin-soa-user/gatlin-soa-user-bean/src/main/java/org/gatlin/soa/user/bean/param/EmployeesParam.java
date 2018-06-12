package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.bean.enums.EmployeeState;

public class EmployeesParam extends SoaParam {

	private static final long serialVersionUID = 277292290545550414L;

	private Long id;
	private Long uid;
	private String name;
	private String mobile;
	private String identity;
	private Integer companyId;
	private String companyName;
	private EmployeeState state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}

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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public EmployeeState getState() {
		return state;
	}
	
	public void setState(EmployeeState state) {
		this.state = state;
	}
}
