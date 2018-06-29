package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

public class DeliveryWindow extends HashMap<String, String> {

	private static final long serialVersionUID = -6481474610932442693L;

	// 计划配送时间范围的开始日期和时间，格式为 ISO 8601
	public DeliveryWindow startDateTime(String startDateTime) {
		this.put("StartDateTime", startDateTime);
		return this;
	}
	
	// 计划配送时间范围的结束日期和时间，格式为 ISO 8601
	public DeliveryWindow endDateTime(String endDateTime) {
		this.put("EndDateTime", endDateTime);
		return this;
	}
}
