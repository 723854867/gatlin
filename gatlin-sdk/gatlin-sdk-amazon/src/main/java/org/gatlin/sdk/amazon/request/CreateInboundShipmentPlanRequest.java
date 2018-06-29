package org.gatlin.sdk.amazon.request;

import org.gatlin.sdk.amazon.AmazonUtil;
import org.gatlin.sdk.amazon.bean.enums.LabelPrepPreference;
import org.gatlin.sdk.amazon.bean.model.Address;
import org.gatlin.sdk.amazon.bean.model.InboundShipmentPlanRequestItem;
import org.gatlin.sdk.amazon.response.CreateInboundShipmentPlanResponse;

/**
 * CreateInboundShipmentPlan 操作的最大请求限额为 30 个，恢复速率为每秒钟 2 个请求
 * 
 * @author lynn
 */
public class CreateInboundShipmentPlanRequest extends AmazonRequest<CreateInboundShipmentPlanResponse, CreateInboundShipmentPlanRequest> {
	
	public CreateInboundShipmentPlanRequest() {
		super("CreateInboundShipmentPlan", "2010-10-01", "FulfillmentInboundShipment/2010-10-01");
	}

	/**
	 * 邮寄地址信息。
	 * 
	 * @param shipFromAddress
	 * @return
	 */
	public CreateInboundShipmentPlanRequest shipFromAddress(Address address) {
		_addParams(AmazonUtil.wrap("ShipFromAddress", address));
		return this;
	}
	
	/**
	 * 您对入库货件标签准备的选项设置(可选)
	 * 
	 * @param address
	 * @return
	 */
	public CreateInboundShipmentPlanRequest labelPrepPreference(LabelPrepPreference labelPrepPreference) { 
		_addParam("LabelPrepPreference", labelPrepPreference.name());
		return this;
	}
	
	// 入库货件中各商品的 SKU 和数量信息
	public CreateInboundShipmentPlanRequest inboundShipmentPlanRequestItems(InboundShipmentPlanRequestItem item) { 
		_addParams(AmazonUtil.wrap("InboundShipmentPlanRequestItems", item));
		return this;
	}
}
