package org.gatlin.soa.authority.bean.param;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.lang.StringUtil;

public class ApisParam extends SoaParam {

	private static final long serialVersionUID = -3738944723164701202L;

	private String path;
	private String desc;
	private Integer deviceMod;
	private Boolean serial;
	private Integer securityLevel;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getDeviceMod() {
		return deviceMod;
	}

	public void setDeviceMod(Integer deviceMod) {
		this.deviceMod = deviceMod;
	}

	public Boolean getSerial() {
		return serial;
	}

	public void setSerial(Boolean serial) {
		this.serial = serial;
	}

	public Integer getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	@Override
	public void verify() {
		super.verify();
		if (StringUtil.hasText(path))
			this.query.like("path", path);
		if (StringUtil.hasText(desc))
			this.query.like("desc", desc);
		if (null != deviceMod)
			this.query.eq("device_mod", deviceMod);
		if (null != serial)
			this.query.eq("serial", serial ? 1 : 0);
		if (null != securityLevel)
			this.query.eq("security_level", securityLevel);
	}
}
