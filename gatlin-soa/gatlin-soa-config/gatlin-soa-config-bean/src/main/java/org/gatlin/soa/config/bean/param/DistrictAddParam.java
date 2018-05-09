package org.gatlin.soa.config.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.config.bean.enums.DistrictLevel;
import org.gatlin.util.lang.StringUtil;

public class DistrictAddParam extends SoaParam {

	private static final long serialVersionUID = 6536056963738694659L;

	@NotEmpty
	private String code;
	@NotEmpty
	private String name;
	private String abname;
	private String parent;
	@NotNull
	private DistrictLevel level;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public DistrictLevel getLevel() {
		return level;
	}
	
	public void setLevel(DistrictLevel level) {
		this.level = level;
	}
	
	@Override
	public void verify() {
		super.verify();
		if (null == abname)
			this.abname = StringUtil.EMPTY;
		if (null == parent)
			this.parent = StringUtil.EMPTY;
		// 只有省才有简称
		if (level == DistrictLevel.PROVINCE)
			Assert.hasText(CoreCode.PARAM_ERR, abname);
		else
			Assert.hasNoText(CoreCode.PARAM_ERR, abname);
	}
}
