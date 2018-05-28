package org.gatlin.soa.alipay.bean.model;

import org.gatlin.soa.alipay.bean.param.RechargeParam;
import org.gatlin.soa.bean.model.ValidatorContext;

public class RechargeContext extends ValidatorContext<RechargeParam> {

	private static final long serialVersionUID = 8229192918584235394L;
	
	public RechargeContext() {}

	public RechargeContext(RechargeParam param) {
		super(param);
	}
}
