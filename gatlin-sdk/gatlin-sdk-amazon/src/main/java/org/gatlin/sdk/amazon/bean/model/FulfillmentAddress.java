package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

public class FulfillmentAddress extends HashMap<String, String> {

	private static final long serialVersionUID = -4489631003330449120L;

	// 收件人的姓名
	public FulfillmentAddress name(String name) {
		this.put("Name", name);
		return this;
	}
	
	// 收件人的街道地址信息
	public FulfillmentAddress line1(String line1) {
		this.put("Line1", line1);
		return this;
	}
	
	// 其他街道地址信息（如果需要）(可选)
	public FulfillmentAddress line2(String line2) {
		this.put("Line2", line2);
		return this;
	}
	
	// 其他街道地址信息（如果需要）(可选)
	public FulfillmentAddress line3(String line3) {
		this.put("Line3", line3);
		return this;
	}
	
	// 收件人所在的城市.必填项，日本除外。请勿在日本使用。
	public FulfillmentAddress city(String city) {
		this.put("City", city);
		return this;
	}
	
	// 邮政编码（发往美国的货件必须填写）
	public FulfillmentAddress postalCode(String postalCode) {
		this.put("PostalCode", postalCode);
		return this;
	}
	
	// 收件人所在的国家/地区两位数代码
	public FulfillmentAddress countryCode(String countryCode) {
		this.put("CountryCode", countryCode);
		return this;
	}
	
	// 收件人所在的区或县
	public FulfillmentAddress districtOrCounty(String districtOrCounty) {
		this.put("DistrictOrCounty", districtOrCounty);
		return this;
	}
	
	// 收件人所在的省/自治区/直辖市代码
	public FulfillmentAddress stateOrProvinceCode(String stateOrProvinceCode) {
		this.put("StateOrProvinceCode", stateOrProvinceCode);
		return this;
	}
	
	// 收件人的电话号码(可选)
	public FulfillmentAddress phoneNumber(String phoneNumber) {
		this.put("PhoneNumber", phoneNumber);
		return this;
	}
}