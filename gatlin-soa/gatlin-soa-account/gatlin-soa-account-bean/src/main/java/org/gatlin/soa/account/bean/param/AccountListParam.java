package org.gatlin.soa.account.bean.param;

import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.param.SoaParam;

public class AccountListParam extends SoaParam {

	private static final long serialVersionUID = 8657510351605476264L;

	private Long uid;
	private AccountType type;
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public AccountType getType() {
		return type;
	}
	
	public void setType(AccountType type) {
		this.type = type;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != uid)
			this.query.eq("uid", uid);
		if (null != type)
			this.query.eq("type", type.mark());
	}
}
