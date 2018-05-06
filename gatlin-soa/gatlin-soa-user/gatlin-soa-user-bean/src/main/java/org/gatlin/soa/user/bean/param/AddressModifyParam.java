package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.util.validate.Mobile;

public class AddressModifyParam extends SoaLidParam {

	private static final long serialVersionUID = -1824255171495807061L;

	@NotEmpty
	private String memo;
	private String city;
	private Integer cityCode;
	private String county;
	private Integer countyCode;
	private String province;
	private Integer provinceCode;
	@NotEmpty
	private String detail;
	private Boolean used;
	@NotEmpty
	private String contacts;
	@Mobile
	private String contactsMobile;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Integer getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(Integer countyCode) {
		this.countyCode = countyCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Boolean getUsed() {
		return used;
	}
	
	public void setUsed(Boolean used) {
		this.used = used;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}
}
