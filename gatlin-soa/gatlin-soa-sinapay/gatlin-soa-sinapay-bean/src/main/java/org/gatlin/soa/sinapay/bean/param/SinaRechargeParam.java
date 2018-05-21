package org.gatlin.soa.sinapay.bean.param;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.soa.bean.param.RechargeParam;

public class SinaRechargeParam extends RechargeParam {

	private static final long serialVersionUID = -6838714136767257758L;

	// 是否对私充值
	private boolean personal;
	private AccountType accountType;
	
	public boolean isPersonal() {
		return personal;
	}
	
	public void setPersonal(boolean personal) {
		this.personal = personal;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	@Override
	public void verify() {
		super.verify();
		switch (getGoodsType()) {
		case 1:
			this.personal = true;
			break;
		default:
			break;
		}
	}
}
