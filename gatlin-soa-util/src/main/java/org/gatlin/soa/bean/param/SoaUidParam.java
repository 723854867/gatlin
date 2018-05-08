package org.gatlin.soa.bean.param;

public class SoaUidParam extends SoaParam {

	private static final long serialVersionUID = 689991486966887994L;

	private Long uid;
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null == uid)
			this.uid = getUser().getId();
	}
}
