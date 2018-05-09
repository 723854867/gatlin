package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.validate.Mobile;

public class AddressAddparam extends SoaParam {

	private static final long serialVersionUID = 3699556394585446042L;

	@NotEmpty
	private String memo;
	@NotEmpty
	private String county;
	@NotEmpty
	private String detail;
	private boolean used;
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
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
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
