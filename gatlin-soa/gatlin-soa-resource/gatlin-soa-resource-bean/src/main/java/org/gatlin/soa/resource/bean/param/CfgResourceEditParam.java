package org.gatlin.soa.resource.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaIdParam;
import org.gatlin.util.bean.enums.CacheUnit;

public class CfgResourceEditParam extends SoaIdParam {

	private static final long serialVersionUID = 2441490287003228455L;

	@Min(1)
	private int type;
	@NotEmpty
	private String name;
	@Min(0)
	private int minimum;
	@Min(0)
	private int maximum;
	@Min(1)
	private int cacheSize;
	@NotEmpty
	private String directory;
	@NotNull
	private CacheUnit cacheUnit;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public CacheUnit getCacheUnit() {
		return cacheUnit;
	}

	public void setCacheUnit(CacheUnit cacheUnit) {
		this.cacheUnit = cacheUnit;
	}

	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(CoreCode.PARAM_ERR, maximum >= minimum);
	}
}
