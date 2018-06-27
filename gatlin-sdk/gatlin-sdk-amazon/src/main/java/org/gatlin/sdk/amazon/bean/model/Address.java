package org.gatlin.sdk.amazon.bean.model;

import java.util.HashMap;

public class Address extends HashMap<String, String> {

	private static final long serialVersionUID = -7559879000921043347L;
	
	// 名称或公司名称
	public Address name(String name) {
		this.put("Name", name);
		return this;
	}
	
	// 城市
	public Address city(String city) {
		this.put("City", city);
		return this;
	}
	
	// 邮政编码(可选)
	public Address postalCode(String postalCode) {
		this.put("PostalCode", postalCode);
		return this;
	}
	
	// 国家/地区代码
	public Address countryCode(String countryCode) {
		this.put("CountryCode", countryCode);
		return this;
	}
	
	// 街道地址信息
	public Address addressLine1(String addressLine1) {
		this.put("addressLine1", addressLine1);
		return this;
	}
	
	// 其他街道地址信息（如果需要）(可选)
	public Address addressLine2(String addressLine2) {
		this.put("addressLine2", addressLine2);
		return this;
	}
	
	// 区或县(可选)
	public Address districtOrCounty(String districtOrCounty) {
		this.put("DistrictOrCounty", districtOrCounty);
		return this;
	}
	
	// 省/自治区/直辖市代码(可选)
	public Address stateOrProvinceCode(String stateOrProvinceCode) {
		this.put("StateOrProvinceCode", stateOrProvinceCode);
		return this;
	}
}
