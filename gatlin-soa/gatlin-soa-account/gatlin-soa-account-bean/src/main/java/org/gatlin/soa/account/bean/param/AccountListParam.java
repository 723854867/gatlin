package org.gatlin.soa.account.bean.param;

import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;

public class AccountListParam extends SoaParam {

	private static final long serialVersionUID = 8657510351605476264L;

	private Long owner;
	private AccountType type;
	private TargetType ownerType;
	
	public Long getOwner() {
		return owner;
	}
	
	public void setOwner(Long owner) {
		this.owner = owner;
	}
	
	public AccountType getType() {
		return type;
	}
	
	public void setType(AccountType type) {
		this.type = type;
	}
	
	public TargetType getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(TargetType ownerType) {
		this.ownerType = ownerType;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != owner)
			this.query.eq("owner", owner);
		if (null != type)
			this.query.eq("type", type.mark());
		if (null != ownerType)
			this.query.eq("owner_type", ownerType.mark());
	}
}
