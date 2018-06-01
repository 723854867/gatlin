package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.sdk.sinapay.bean.enums.BidState;
import org.gatlin.soa.sinapay.bean.enums.BidPurpose;
import org.gatlin.util.bean.Identifiable;

public class SinaBid implements Identifiable<String> {

	private static final long serialVersionUID = 1792538358785799583L;

	@Id
	private String id;
	private String bizId;
	private BidState state;
	private String borrower;
	private BidPurpose purpose;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public BidState getState() {
		return state;
	}

	public void setState(BidState state) {
		this.state = state;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public BidPurpose getPurpose() {
		return purpose;
	}

	public void setPurpose(BidPurpose purpose) {
		this.purpose = purpose;
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
