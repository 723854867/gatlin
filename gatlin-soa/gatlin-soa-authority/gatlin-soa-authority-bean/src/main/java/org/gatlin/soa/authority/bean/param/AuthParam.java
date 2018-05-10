package org.gatlin.soa.authority.bean.param;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.bean.param.SoaParam;

public class AuthParam extends SoaParam {

	private static final long serialVersionUID = -7110802930846787502L;

	@Min(1)
	private long sid;
	@NotNull
	private Set<Integer> tid;

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public void setTid(Set<Integer> tid) {
		this.tid = tid;
	}
	
	public Set<Integer> getTid() {
		return tid;
	}

}
