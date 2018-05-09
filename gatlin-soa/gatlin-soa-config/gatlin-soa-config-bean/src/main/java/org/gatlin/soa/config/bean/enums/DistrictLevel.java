package org.gatlin.soa.config.bean.enums;

public enum DistrictLevel {

	// 国
	COUNTRY(0),
	// 省
	PROVINCE(1),
	// 市
	CITY(2),
	// 县
	COUNTY(3);
	
	private int mark;
	
	private DistrictLevel(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
