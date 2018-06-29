package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

import org.gatlin.sdk.amazon.AmazonUtil;

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
	
	// 商品数量
	public CreateFulfillmentOrderItem quantity(int quantity) {
		this.put("Quantity", String.valueOf(quantity));
		return this;
	}
	
	// 给礼物收件人的消息(可选)
	public CreateFulfillmentOrderItem giftMessage(String giftMessage) {
		this.put("GiftMessage", giftMessage);
		return this;
	}
	
	// 面向收件人的商品详情文本(可选)
	public CreateFulfillmentOrderItem displayableComment(String displayableComment) {
		this.put("DisplayableComment", displayableComment);
		return this;
	}
	
	// 商品的亚马逊配送网络 SKU(可选)
	public CreateFulfillmentOrderItem fulfillmentNetworkSKU(String fulfillmentNetworkSKU) {
		this.put("FulfillmentNetworkSKU", fulfillmentNetworkSKU);
		return this;
	}
	
	// 卖家为此商品指定的价格(可选)
	public CreateFulfillmentOrderItem perUnitDeclaredValue(Currency currency) {
		this.putAll(AmazonUtil.wrap("PerUnitDeclaredValue", currency));
		return this;
	}
	
	// 买家需要对货到付款 (COD) 订单中的此商品支付的金额(可选)
	public CreateFulfillmentOrderItem perUnitPrice(Currency currency) {
		this.putAll(AmazonUtil.wrap("PerUnitPrice", currency));
		return this;
	}
	
	// 买家需要对货到付款 (COD) 订单中的此商品支付的金额的税费(可选)
	public CreateFulfillmentOrderItem perUnitTax(Currency currency) {
		this.putAll(AmazonUtil.wrap("PerUnitTax", currency));
		return this;
	}
}
