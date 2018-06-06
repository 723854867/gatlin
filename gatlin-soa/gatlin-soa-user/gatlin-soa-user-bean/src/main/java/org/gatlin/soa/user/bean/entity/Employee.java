package org.gatlin.soa.user.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.soa.user.bean.enums.EmployeeState;
import org.gatlin.util.bean.Identifiable;

public class Employee implements Identifiable<Long> {

	private static final long serialVersionUID = 8893435011458032904L;

	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private int companyId;
	private EmployeeState state;
	private int created;
	private int updated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public EmployeeState getState() {
		return state;
	}
	
	public void setState(EmployeeState state) {
		this.state = state;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}
	
	public int getUpdated() {
		return updated;
	}
	
	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public Long key() {
		return this.id;
	}
}
