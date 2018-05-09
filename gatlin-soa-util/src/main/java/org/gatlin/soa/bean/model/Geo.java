package org.gatlin.soa.bean.model;

import java.io.Serializable;

public class Geo implements Serializable {

	private static final long serialVersionUID = -1942267163086433381L;

	private String city;
	private String county;
	private String country;
	private String province;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
