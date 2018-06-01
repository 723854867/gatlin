package org.gatlin.soa.user.bean.model;

import java.io.Serializable;

import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.model.ResourceInfo;

public class BankCardInfo implements Serializable {

	private static final long serialVersionUID = -5837414526750314792L;

	private String id;
	private long owner;
	private String bankId;
	private String bankNo;
	private boolean used;
	private String mobile;
	private String city;
	private String province;
	private String branch;
	private int created;
	private String ownerName;
	private String ownerPhone;
	private TargetType ownerType;
	private String ownerIdentity;
	private ResourceInfo icon;

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
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}
	
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	
	public TargetType getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(TargetType ownerType) {
		this.ownerType = ownerType;
	}

	public String getOwnerIdentity() {
		return ownerIdentity;
	}

	public void setOwnerIdentity(String ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}
	
	public ResourceInfo getIcon() {
		return icon;
	}
	
	public void setIcon(ResourceInfo icon) {
		this.icon = icon;
	}
}
