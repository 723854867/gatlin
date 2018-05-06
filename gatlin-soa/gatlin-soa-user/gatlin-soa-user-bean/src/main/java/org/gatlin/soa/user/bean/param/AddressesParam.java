package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.StringUtil;

public class AddressesParam extends SoaParam {

	private static final long serialVersionUID = -645981613292805919L;

	private Long id;
	private Long uid;
	private String city;
	private String county;
	private String province;
	private String contacts;
	private String contactsMobile;
	private Boolean deleted;
	private Boolean used;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	@Override
	public void verify() {
		super.verify();
		if (null != id)
			this.query.eq("id", id);
		if (null != uid)
			this.query.eq("uid", uid);
		if (StringUtil.hasText(city))
			this.query.like("city", city);
		if (StringUtil.hasText(city))
			this.query.like("county", county);
		if (StringUtil.hasText(city))
			this.query.like("province", province);
		if (StringUtil.hasText(city))
			this.query.like("contacts", contacts);
		if (StringUtil.hasText(city))
			this.query.like("contacts_mobile", contactsMobile);
		if (null != deleted) 
			this.query.eq("deleted", deleted ? 1 : 0);
		if (null != used)
			this.query.eq("used", used ? 1 : 0);
	}
}
