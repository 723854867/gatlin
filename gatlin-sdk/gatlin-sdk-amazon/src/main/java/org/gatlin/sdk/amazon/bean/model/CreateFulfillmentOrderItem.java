package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

public class CreateFulfillmentOrderItem extends HashMap<String, String> {

	private static final long serialVersionUID = 31077772232637558L;

	// 商品的卖家 SKU
	public CreateFulfillmentOrderItem sellerSKU(String sellerSKU) {
		this.put("SellerSKU", sellerSKU);
		return this;
	}
	
	// 您创建的用于跟踪配送订单商品的配送订单商品编号。您可以使用此值区分具有相同 SellerSKU 的多件配送商品。例如，您可以向具有相同 SellerSKU 值但 GiftMessage 
	// 值不同的配送订单中的两件商品分配不同的 SellerFulfillmentOrderItemId 值。
	public CreateFulfillmentOrderItem sellerFulfillmentOrderItemId(String OrderItemId) {
		this.put("SellerFulfillmentOrderItemId", OrderItemId);
		return this;
	}
}
