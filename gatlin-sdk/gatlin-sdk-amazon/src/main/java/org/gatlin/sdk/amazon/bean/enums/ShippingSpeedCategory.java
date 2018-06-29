package org.gatlin.sdk.amazon.bean.enums;

/**
 * 各商城的配送方式服务水平协议有所不同。查看您所在商城的亚马逊卖家平台网站以了解配送方式服务水平协议和配送费用
 * 
 * @author lynn
 */
public enum ShippingSpeedCategory {

	// 标准配送方式
	Standard,
	// 加急配送方式
	Expedited,
	// 优先配送方式
	Priority,
	// 预约送货上门配送方式：仅适用于日本
	ScheduledDelivery;
}
