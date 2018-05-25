package org.gatlin.soa.bean.model;

import java.io.Serializable;

import org.gatlin.soa.bean.param.SoaParam;

public abstract class ValidatorContext<PARAM extends SoaParam> implements Serializable {

	private static final long serialVersionUID = 2828752997435128434L;

	private PARAM param;
	
	protected ValidatorContext() {}
	
	protected ValidatorContext(PARAM param) {
		this.param = param;
	}
	
	public PARAM getParam() {
		return param;
	}
	
	public void setParam(PARAM param) {
		this.param = param;
	}
}
