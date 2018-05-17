package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class SinaBankCard implements Identifiable<String> {

	private static final long serialVersionUID = 4659851172140139451L;

	@Id
	private String id;
	private long uid;
	private int used;
	private String ip;
	private String city;
	private String bankId;
	private String bankNo;
	private String ticket;
	private String mobile;
	private String branch;
	private String sinaUid;
	private String cardId;
	private String sinaCardId;
	private String province;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public long getUid() {
		return uid;
	}
	
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public int getUsed() {
		return used;
	}
	
	public void setUsed(int used) {
		this.used = used;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getSinaUid() {
		return sinaUid;
	}
	
	public void setSinaUid(String sinaUid) {
		this.sinaUid = sinaUid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getSinaCardId() {
		return sinaCardId;
	}
	
	public void setSinaCardId(String sinaCardId) {
		this.sinaCardId = sinaCardId;
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
