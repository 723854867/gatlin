package org.gatlin.soa.sinapay.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.sdk.sinapay.bean.enums.WithdrawState;
import org.gatlin.util.bean.Identifiable;

public class SinaLoanout implements Identifiable<String> {

	private static final long serialVersionUID = -5469698031493823771L;

	@Id
	private String id;
	private String bidId;
	private BigDecimal amount;
	private WithdrawState state;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public WithdrawState getState() {
		return state;
	}
	
	public void setState(WithdrawState state) {
		this.state = state;
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
