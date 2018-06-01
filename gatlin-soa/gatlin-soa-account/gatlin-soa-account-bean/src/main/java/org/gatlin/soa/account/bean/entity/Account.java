package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.bean.Identifiable;

public class Account implements Identifiable<Long> {

	private static final long serialVersionUID = 7283759456351585371L;

	@Id
	@GeneratedValue
	private long id;
	private long owner;
	private AccountType type;
	private BigDecimal usable;
	private BigDecimal frozen;
	private TargetType ownerType;
	private int created;
	private int updated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public BigDecimal getUsable() {
		return usable;
	}

	public void setUsable(BigDecimal usable) {
		this.usable = usable;
	}

	public BigDecimal getFrozen() {
		return frozen;
	}

	public void setFrozen(BigDecimal frozen) {
		this.frozen = frozen;
	}

	public TargetType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(TargetType ownerType) {
		this.ownerType = ownerType;
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
	public Long key() {
		return this.id;
	}
}
