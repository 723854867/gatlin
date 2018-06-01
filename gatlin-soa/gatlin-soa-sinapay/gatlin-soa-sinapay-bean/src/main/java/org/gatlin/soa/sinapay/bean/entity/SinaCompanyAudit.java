package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.sdk.sinapay.bean.enums.CompanyAuditState;
import org.gatlin.util.bean.Identifiable;

public class SinaCompanyAudit implements Identifiable<String> {

	private static final long serialVersionUID = -41379286424157848L;

	@Id
	private String id;
	private String ip;
	private String sinaUid;
	private String city;
	private String cardId;
	private String province;
	private String mobile;
	private String branch;
	private String bankId;
	private String bankNo;
	private String auditMsg;
	private CompanyAuditState state;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
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
	
	public String getSinaUid() {
		return sinaUid;
	}
	
	public void setSinaUid(String sinaUid) {
		this.sinaUid = sinaUid;
	}
	
	public String getAuditMsg() {
		return auditMsg;
	}
	
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}
	
	public CompanyAuditState getState() {
		return state;
	}
	
	public void setState(CompanyAuditState state) {
		this.state = state;
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
