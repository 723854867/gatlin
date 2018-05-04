package org.gatlin.soa.authority.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.soa.bean.param.SoaParam;

public class AuthParam extends SoaParam {

	private static final long serialVersionUID = -7110802930846787502L;

	@Min(1)
	private long sid;
	@Min(1)
	private long tid;

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}
}
