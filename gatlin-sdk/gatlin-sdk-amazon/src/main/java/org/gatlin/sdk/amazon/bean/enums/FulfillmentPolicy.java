package org.gatlin.sdk.amazon.bean.enums;

/**
 * 指明应该如何处理配送订单中无法配送的商品。
 * 
 * @author lynn
 */
public enum FulfillmentPolicy {

	/**
	 * 如果在订单中的任一货件进入 Pending 状态（已开始从库存中拣货）之前，该配送订单中的商品被确定为无法配送，
	 * 则整个订单将被视为无法配送。但是，如果在订单中的货件进入 Pending 状态之后，该配送订单中的商品被确定为无法配送，
	 * 亚马逊将尽可能地取消配送订单。 默认值
	 */
	FillOrKill,
	
	/**
	 * 配送订单中的所有可配送商品。在亚马逊发送或卖家取消所有商品前，配送订单将一直处于“处理中”状态
	 */
	FillAll,
	
	/**
	 * 配送订单中的所有可配送商品。由亚马逊取消订单中的所有无法配送商品。
	 */
	FillAllAvailable;
}
