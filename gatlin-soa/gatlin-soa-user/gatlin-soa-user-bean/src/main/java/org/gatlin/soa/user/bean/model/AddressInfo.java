package org.gatlin.soa.user.bean.model;

import java.io.Serializable;

import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.user.bean.entity.UserAddress;

public class AddressInfo implements Serializable {

	private static final long serialVersionUID = 8886212743669638638L;

	private long id;
	private Geo geo;
	private long uid;
	private String memo;
	private String detail;
	private String contacts;
	private boolean used;
	private String contactsMobile;
	private int created;
	private boolean deleted;
	
	public AddressInfo() {}
	
	public AddressInfo(UserAddress address, Geo geo) {
		this.geo = geo;
		this.id = address.getId();
		this.uid = address.getUid();
		this.memo = address.getMemo();
		this.detail = address.getDetail();
		this.contacts = address.getContacts();
		this.contactsMobile = address.getContactsMobile();
		this.created = address.getCreated();
		this.used = address.isUsed();
		this.deleted = address.isDeleted();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
