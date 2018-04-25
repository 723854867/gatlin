package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.util.PhoneUtil;

public class UsernameParam extends SoaParam {

	private static final long serialVersionUID = 530498799553103189L;

	@NotEmpty
	private String username;
	@NotNull
	private UsernameType usernameType;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public UsernameType getUsernameType() {
		return usernameType;
	}

	public void setUsernameType(UsernameType usernameType) {
		this.usernameType = usernameType;
	}
	
	@Override
	public void verify() {
		super.verify();
		switch (usernameType) {
		case MOBILE:
			Assert.isTrue(PhoneUtil.isMobile(username), CoreCode.PARAM_ERR);
			this.username = "+" + PhoneUtil.getCountryCode(username) + PhoneUtil.getNationalNumber(username);
			break;
		default:
			break;
		}
	}
}
