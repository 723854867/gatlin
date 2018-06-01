package org.gatlin.soa.user.bean.entity;

import javax.persistence.Id;

import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.bean.Identifiable;

public class BankCard implements Identifiable<String> {

	private static final long serialVersionUID = 1543997790553681229L;

	@Id
	private String id;
	private long owner;
	private String no;
	private String bankId;
	private boolean used;
	private String mobile;
	private String province;
	private String city;
	private String branch;
	private TargetType ownerType;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public TargetType getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(TargetType ownerType) {
		this.ownerType = ownerType;
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
	public String key() {
		return this.id;
	}
}
