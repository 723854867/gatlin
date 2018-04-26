package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.bean.enums.UsernameType;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.validate.Validator;

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
		case EMAIL:
			Assert.isTrue(CoreCode.PARAM_ERR, Validator.isEmail(username));
			break;
		case MOBILE:
			Assert.isTrue(CoreCode.PARAM_ERR, PhoneUtil.isMobile(username));
			setUsername(PhoneUtil.parseMobile(username));
			break;
		default:
			break;
		}
	}
}
