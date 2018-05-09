package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.util.validate.Mobile;

public class AddressModifyParam extends SoaLidParam {

	private static final long serialVersionUID = -1824255171495807061L;

	private String memo;
	private String county;
	private String detail;
	private Boolean used;
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
