package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.validate.Identity;
import org.gatlin.util.validate.Mobile;

public class CompanyAddParam extends SoaParam {

	private static final long serialVersionUID = -3626675390920435536L;

	@NotEmpty
	private String name;
	@NotEmpty
	private String website;
	@NotEmpty
	private String address;
	@NotEmpty
	private String identity;
	@NotEmpty
	private String licenseAddress;
	@Min(1)
	private long licenseExpiry;
	@NotEmpty
	private String businessScope;
	@NotEmpty
	private String telephone;
	@NotEmpty
	private String email;
	@NotEmpty
	private String summary;
	// 法人姓名
	@NotEmpty
	private String legalPerson;
	// 法人手机号
	@Mobile
	@NotEmpty
	private String legalMobile;
	// 法人身份证号
	@Identity
	@NotEmpty
	private String legalIdentity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getLicenseAddress() {
		return licenseAddress;
	}

	public void setLicenseAddress(String licenseAddress) {
		this.licenseAddress = licenseAddress;
	}

	public long getLicenseExpiry() {
		return licenseExpiry;
	}

	public void setLicenseExpiry(long licenseExpiry) {
		this.licenseExpiry = licenseExpiry;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}

	public String getLegalIdentity() {
		return legalIdentity;
	}

	public void setLegalIdentity(String legalIdentity) {
		this.legalIdentity = legalIdentity;
	}

}
