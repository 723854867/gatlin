package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;

public class AuthParam extends SoaParam {

	private static final long serialVersionUID = -8255644808579094664L;

	@Min(1)
	private long uid;
	@Min(1)
	private int roleId;
	
	public long getUid() {
		return uid;
	}
	
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(CoreCode.FORBID, uid != getUser().getId());
	}
}
