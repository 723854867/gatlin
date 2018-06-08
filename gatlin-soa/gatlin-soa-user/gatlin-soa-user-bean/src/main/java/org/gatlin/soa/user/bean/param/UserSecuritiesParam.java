package org.gatlin.soa.user.bean.param;

import org.gatlin.soa.bean.param.SoaParam;

public class UserSecuritiesParam extends SoaParam {

	private static final long serialVersionUID = 3792687650740252104L;

	private Long uid;
	private String name;
	private String mobile;
	private String identity;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	@Override
	public void verify() {
		super.verify();
		if (null != uid)
			this.query.eq("uid", uid);
		if (null != name)
			this.query.like("realname", name);
		if (null != mobile)
			this.query.like("mobile", mobile);
		if (null != identity)
			this.query.like("identity", identity);
		this.query.orderByDesc("created");
	}
}
