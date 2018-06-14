package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.soa.bean.param.SoaParam;

public class QueryBalanceParam extends SoaParam {

	private static final long serialVersionUID = -7239123443864951554L;

	private Long uid;
	@Null
	private String identity;
	private AccountType accountType;
	@NotNull
	private MemberIdentityType identityType;
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
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
	
	@Override
	public void verify() {
		super.verify();
		if (null == accountType)
			accountType = AccountType.BASIC;
		if (identityType == MemberIdentityType.UID)
			Assert.notNull(CoreCode.PARAM_ERR, uid);
	}
}
