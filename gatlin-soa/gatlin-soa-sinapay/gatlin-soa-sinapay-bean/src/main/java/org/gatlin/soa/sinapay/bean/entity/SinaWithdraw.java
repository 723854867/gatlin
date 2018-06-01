package org.gatlin.soa.sinapay.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.soa.sinapay.bean.enums.SinaWithdrawState;
import org.gatlin.util.bean.Identifiable;

public class SinaWithdraw implements Identifiable<String> {

	private static final long serialVersionUID = -4269306472429420422L;

	@Id
	private String id;
	private BigDecimal amount;
	private String withdrawee;
	private AccountType accountType;
	private SinaWithdrawState state;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getWithdrawee() {
		return withdrawee;
	}

	public void setWithdrawee(String withdrawee) {
		this.withdrawee = withdrawee;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public SinaWithdrawState getState() {
		return state;
	}

	public void setState(SinaWithdrawState state) {
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
