package org.gatlin.sdk.jisu.bean.model;

import java.io.Serializable;

public class JieQiNow implements Serializable {

	private static final long serialVersionUID = -539327366387304372L;
	
	private Integer day;
	private String name;
	private String time;
	private String[] lunar;
	
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTime() {
		return time;
	}
	
	public String[] getLunar() {
		return lunar;
	}
}
