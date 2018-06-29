package org.gatlin.sdk.amazon.request.order;

import java.math.BigDecimal;

import org.gatlin.sdk.amazon.bean.enums.FulfillmentAction;
import org.gatlin.sdk.amazon.bean.enums.FulfillmentPolicy;
import org.gatlin.sdk.amazon.bean.enums.ShippingSpeedCategory;
import org.gatlin.sdk.amazon.request.AmazonRequest;
import org.gatlin.sdk.amazon.response.order.CreateFulfillmentOrderResponse;

/**
 * 请求亚马逊将商品从卖家的 亚马逊物流 库存中配送至目的地地址。
 * <pre>
 * 让亚马逊将商品从亚马逊物流中的卖家库存配送到目的地地址。 如果亚马逊物流中的库存不足以创建配送订单，服务将返回错误。
 * 
 * 暂缓配送订单
 * 当您出于某种原因（如验证付款）而需要推迟订单配送时，可以使用 CreateFulfillmentOrder 操作暂缓配送订单。暂缓配送订单不仅可以防止对订单进行配送，
 * 而且可以防止配送订单中的库存商品被用于亚马逊网站上的亚马逊物流 (FBA)订单。帮助您避免在亚马逊网站上和通过多渠道配送重复销售您的亚马逊库存。要创建暂缓配送
 * 订单，请调用 CreateFulfillmentOrder 操作并指定 FulfillmentAction = Hold。
 * 订单配送可以暂缓两周。两周后，如果您没有发送或取消配送订单，配送订单将被自动取消。那时，配送订单中的库存商品将可以被用于亚马逊网站上的亚马逊物流订单
 * 
 * 操作的最大请求限额为 30 个，恢复速率为每秒钟 2 个请求
 * </pre>
 * 
 * @author lynn
 */
public class CreateFulfillmentOrderRequest extends AmazonRequest<CreateFulfillmentOrderResponse, CreateFulfillmentOrderRequest> {

	public CreateFulfillmentOrderRequest() {
		super("CreateFulfillmentOrder", "2010-10-01", "FulfillmentOutboundShipment/2010-10-01");
	}
	
	// 订单编号
	public CreateFulfillmentOrderRequest orderId(String orderId) {
		return _addParam("SellerFulfillmentOrderId", orderId);
	}
	
	// 指定配送订单应立即配送还是暂缓配送(可选-默认Ship)
	public CreateFulfillmentOrderRequest fulfillmentAction(FulfillmentAction action) {
		return _addParam("FulfillmentAction", action.name());
	}
	
	// 显示给买家的订单编号。一般和 orderId相同。
	public CreateFulfillmentOrderRequest displayableOrderId(String orderId) {
		return _addParam("DisplayableOrderId", orderId);
	}
	
	// 面向买家的订单日期格式为：ISO 8601
	public CreateFulfillmentOrderRequest displayableOrderDateTime(String data) {
		return _addParam("DisplayableOrderDateTime", data);
	}
	
	// 面向买家的订单详情
	public CreateFulfillmentOrderRequest displayableOrderComment(String comment) {
		return _addParam("DisplayableOrderComment", comment);
	}
	
	// 配送方式
	public CreateFulfillmentOrderRequest shippingSpeedCategory(ShippingSpeedCategory catetory) {
		return _addParam("ShippingSpeedCategory", catetory.name());
	}
	
	// 订单配送目的地地址
	public CreateFulfillmentOrderRequest destinationAddress(String address) {
		return _addParam("DestinationAddress", address);
	}
	
	// 指明应该如何处理配送订单中无法配送的商品(可选)
	public CreateFulfillmentOrderRequest fulfillmentPolicy(FulfillmentPolicy policy) {
		return _addParam("FulfillmentPolicy", policy.name());
	}
	
	// 电子邮箱地址列表，亚马逊将使用此列表代表您向买家发送发货通知(可选)
	public CreateFulfillmentOrderRequest notificationEmailList(String emails) {
		return _addParam("NotificationEmailList", emails);
	}
	
	// 货到付款(COD)配送订单的货到付款金额(可选 - 只适用于中国 (CN) 和日本 (JP)。如果在其他商城中指定 CODSettings 参数，系统将返回错误。)
	public CreateFulfillmentOrderRequest CODmoney(BigDecimal CODmoney) {
		return _addParam("CODSettings", CODmoney.toString());
	}
	
	// 要包含在配送订单预览中的商品列表，包含数量
	public CreateFulfillmentOrderRequest items(BigDecimal CODmoney) {
		return _addParam("Items", CODmoney.toString());
	}
	
	public CreateFulfillmentOrderRequest DeliveryWindow(BigDecimal CODmoney) {
		return _addParam("DeliveryWindow", CODmoney.toString());
	}
}
