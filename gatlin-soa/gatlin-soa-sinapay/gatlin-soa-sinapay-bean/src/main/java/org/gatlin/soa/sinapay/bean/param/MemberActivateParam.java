package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.bean.param.SoaParam;

public class MemberActivateParam extends SoaParam {

	private static final long serialVersionUID = -4650081195668940254L;

	private String tid;
	@NotNull
	private MemberType type;
	
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public MemberType getType() {
		return type;
	}
	
	public void setType(MemberType type) {
		this.type = type;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (type == MemberType.ENTERPRISE)
			Assert.hasText(CoreCode.PARAM_ERR, tid);
		else
			this.tid = String.valueOf(getUser().getId());
	}
}
