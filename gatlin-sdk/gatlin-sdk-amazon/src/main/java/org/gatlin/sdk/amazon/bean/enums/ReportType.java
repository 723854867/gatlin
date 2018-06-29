package org.gatlin.sdk.amazon.bean.enums;

public enum ReportType {
	
	// 计划的 XML 订单报告
	_GET_ORDERS_DATA_,
	// 请求或计划的文本文件订单报告
	_GET_FLAT_FILE_ORDERS_DATA_,
	// 待处理订单报告
	_GET_FLAT_FILE_ACTIONABLE_ORDER_DATA_,
	// 文件文件订单报告
	_GET_CONVERGED_FLAT_FILE_ORDER_REPORT_DATA_,
	// 按 SKU 报告排列的商品广告每日绩效，文本文件
	_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_DAILY_DATA_TSV_,
	// 按 SKU 报告排列的商品广告每日绩效，XML 文件
	_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_DAILY_DATA_XML_,
	// 按 SKU 报告排列的商品广告每周绩效，文本文件
	_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_WEEKLY_DATA_TSV_,
	// 按 SKU 报告排列的商品广告每周绩效，XML 文件
	_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_WEEKLY_DATA_XML_,
	// 按 SKU 报告排列的商品广告每月绩效，文本文件
	_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_MONTHLY_DATA_TSV_,
	// 按 SKU 报告排列的商品广告每月绩效，XML 文件
	_GET_PADS_PRODUCT_PERFORMANCE_OVER_TIME_MONTHLY_DATA_XML_;
}
