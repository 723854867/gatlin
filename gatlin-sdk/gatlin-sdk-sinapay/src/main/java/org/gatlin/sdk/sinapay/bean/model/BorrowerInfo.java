package org.gatlin.sdk.sinapay.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;

public class BorrowerInfo implements Serializable {

	private static final long serialVersionUID = -9017736334026291446L;

	// 借款人邮箱地址：可空
	@Column(scale = 13)
	private String email;
	// 借款人固定电话：可空
	@Column(scale = 6)
	private String phone;
	// 借款人婚姻状况：可空
	@Column(scale = 11)
	private String marry;
	// 借款人学历：可空
	@Column(scale = 10)
	private String degree;
	// 借款人手机号
	@Column(scale = 5)
	private String mobile;
	// 摘要：可空
	@Column(scale = 14)
	private String summary;
	// 借款人地址：可空
	@Column(scale = 12)
	private String address;
	// 借款人工作单位：可空
	@Column(scale = 7)
	private String company;
	@Column(scale = 4)
	private String purpose;
	// 借款人工作年限：可空
	@Column(scale = 8)
	private Integer workAge;
	@Column(scale = 1)
	private String identity;
	// 借款人月收入：可空
	@Column(scale = 9)
	private BigDecimal salary;
	@Column(scale = 3)
	private BigDecimal amount;
	// 扩展信息：可空
	@Column(scale = 15)
	private String extendParam;
	@Column(scale = 2)
	private MemberIdentityType identityType = MemberIdentityType.UID;

	public BorrowerInfo(String identity, String mobile, String purpose, BigDecimal amount) {
		this.amount = amount;
		this.mobile = mobile;
		this.purpose = purpose;
		this.identity = identity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getWorkAge() {
		return workAge;
	}

	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getExtendParam() {
		return extendParam;
	}

	public void setExtendParam(String extendParam) {
		this.extendParam = extendParam;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getIdentity() {
		return identity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public MemberIdentityType getIdentityType() {
		return identityType;
	}
}
