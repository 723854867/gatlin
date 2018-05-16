package org.gatlin.soa.user.bean.param;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.bean.enums.UserMod;

public class UserModifyParam extends SoaParam {

	private static final long serialVersionUID = 6695990283022601500L;

	private String name;
	private Integer mod;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getMod() {
		return mod;
	}
	
	public void setMod(Integer mod) {
		this.mod = mod;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null != mod) 
			Assert.isTrue(CoreCode.PARAM_ERR, UserMod.checkModify(mod));
	}
}
