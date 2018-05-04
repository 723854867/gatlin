package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.bean.UserCode;

public class PwdModifyParam extends SoaParam {

	private static final long serialVersionUID = -1853694800314140918L;

	@NotEmpty
	private String pwd;
	@NotEmpty
	private String opwd;
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getOpwd() {
		return opwd;
	}
	
	public void setOpwd(String opwd) {
		this.opwd = opwd;
	}
	
	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(UserCode.PWD_UNMODIFIED, !pwd.equals(opwd));
	}
}
