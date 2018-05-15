package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.validate.Identity;
import org.gatlin.util.validate.Mobile;

public class RealnameParam extends SoaParam {

	private static final long serialVersionUID = 8865525744560613504L;

	@Mobile
	private String mobile;
	@NotEmpty
	@Identity
	private String identity;
	@NotEmpty
	private String realname;
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getIdentity() {
		return identity;
	}
	
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getRealname() {
		return realname;
	}
	
	public void setRealname(String realname) {
		this.realname = realname;
	}
}
