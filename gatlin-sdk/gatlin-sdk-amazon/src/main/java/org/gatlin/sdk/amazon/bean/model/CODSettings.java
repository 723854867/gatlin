package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

import org.gatlin.sdk.amazon.AmazonUtil;

public class CODSettings extends HashMap<String, String> {

	private static final long serialVersionUID = -1971147771942820118L;

	// 指明此配送订单是否要求货到付款(可选-默认false)。如果指定 IsCODRequired=true， 还必须指定 PerUnitPrice
	public CODSettings isCODRequired(boolean isCODRequired) {
		this.put("IsCODRequired", String.valueOf(isCODRequired));
		return this;
	}
	
	// 买家需要对货到付款订单支付的金额(可选)
	public CODSettings CODCharge(Currency currency) {
		this.putAll(AmazonUtil.wrap("CODCharge", currency));
		return this;
	}
	
	// 买家需要对货到付款订单支付的税费(可选)
	public CODSettings CODChargeTax(Currency currency) {
		this.putAll(AmazonUtil.wrap("CODChargeTax", currency));
		return this;
	}
	
	// 买家需要对货到付款订单支付的运费金额(可选)
	public CODSettings shippingCharge(Currency currency) {
		this.putAll(AmazonUtil.wrap("ShippingCharge", currency));
		return this;
	}
	
	// 买家需要对货到付款订单支付的运费的税费(可选)
	public CODSettings shippingChargeTax(Currency currency) {
		this.putAll(AmazonUtil.wrap("ShippingChargeTax", currency));
		return this;
	}
}
