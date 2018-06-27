package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

import org.gatlin.sdk.amazon.bean.enums.Condition;

public class InboundShipmentPlanRequestItem extends HashMap<String, String> {

	private static final long serialVersionUID = -3494774686169112897L;

	// 商品的卖家 SKU
	public InboundShipmentPlanRequestItem sellerSKU(String sellerSKU) { 
		this.put("SellerSKU", sellerSKU);
		return this;
	}
	
	// 商品的亚马逊标准识别号(可选)
	public InboundShipmentPlanRequestItem ASIN(String ASIN) { 
		this.put("ASIN", ASIN);
		return this;
	}
	
	// 商品的状况(可选)
	public InboundShipmentPlanRequestItem Condition(Condition condition) { 
		this.put("Condition", condition.name());
		return this;
	}
	
	// 商品数量(可选)
	public InboundShipmentPlanRequestItem Quantity(int quantity) { 
		this.put("quantity", String.valueOf(quantity));
		return this;
	}
	
	// 每个包装箱中的商品数量（仅针对原厂包装发货商品）。请注意，QuantityInCase x 入库货件中的包装箱数 = Quantity。
	// 另请注意，入库货件中的所有包装箱必须是原厂包装发货或者混装发货。因此，当您提交 CreateInboundShipmentPlan 操作时，必须对货件中的每件商品提供 QuantityInCase 的值，或者都不提供。
	public InboundShipmentPlanRequestItem quantityInCase(int quantityInCase) { 
		this.put("QuantityInCase", String.valueOf(quantityInCase));
		return this;
	}
}
