package org.gatlin.soa.config.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum DistrictLevel implements IEnum {

	// 国
	COUNTRY(0),
	// 省
	PROVINCE(1) {
		@Override
		public DistrictLevel parentLevel() {
			return COUNTRY;
		}
	},
	// 市
	CITY(2) {
		@Override
		public DistrictLevel parentLevel() {
			return PROVINCE;
		}
	},
	// 县
	COUNTY(3) {
		@Override
		public DistrictLevel parentLevel() {
			return CITY;
		}
	};
	
	private int mark;
	
	private DistrictLevel(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
	
	public DistrictLevel parentLevel() {
		throw new UnsupportedOperationException(name() + " has no parent level");
	}
}
