package org.gatlin.soa.config.bean.param;

import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.config.bean.enums.DistrictType;

public class DistrictModifyParam extends SoaSidParam {

	private static final long serialVersionUID = -4741723841884432803L;

	private String name;
	private String abname;
	private String parent;
	private Boolean valid;
	private DistrictType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbname() {
		return abname;
	}

	public void setAbname(String abname) {
		this.abname = abname;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public Boolean getValid() {
		return valid;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public DistrictType getType() {
		return type;
	}

	public void setType(DistrictType type) {
		this.type = type;
	}
}
