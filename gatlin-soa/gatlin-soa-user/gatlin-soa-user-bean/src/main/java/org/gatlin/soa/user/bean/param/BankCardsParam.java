package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.EnumUtil;

public class BankCardsParam extends SoaParam {

	private static final long serialVersionUID = 7365230094909022034L;

	private String id;
	private Long owner;
	private String city;
	@Min(0)
	private int ownerType;
	private String branch;
	private String bankNo;
	private String mobile;
	private String province;
	private String ownerName;
	private Integer timeStop;
	private Integer timeStart;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
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

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}
	
	public Integer getTimeStop() {
		return timeStop;
	}
	
	public void setTimeStop(Integer timeStop) {
		this.timeStop = timeStop;
	}
	
	public Integer getTimeStart() {
		return timeStart;
	}
	
	public void setTimeStart(Integer timeStart) {
		this.timeStart = timeStart;
	}
	
	@Override
	public void verify() {
		super.verify();
		TargetType targetType = EnumUtil.valueOf(TargetType.class, ownerType);
		if (null == targetType)
			this.ownerType = TargetType.USER.mark();
	}
}
