package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.soa.bean.param.SoaParam;

public class QueryBalanceParam extends SoaParam {

	private static final long serialVersionUID = -7239123443864951554L;

	@NotEmpty
	private String identity;
	@NotNull
	private AccountType accountType;
	@NotNull
	private MemberIdentityType identityType;
	
	public String getIdentity() {
		return identity;
	}
	
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	public MemberIdentityType getIdentityType() {
		return identityType;
	}
	
	public void setIdentityType(MemberIdentityType identityType) {
		this.identityType = identityType;
	}
}
