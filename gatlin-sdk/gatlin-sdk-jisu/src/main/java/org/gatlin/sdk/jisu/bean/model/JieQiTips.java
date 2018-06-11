package org.gatlin.sdk.jisu.bean.model;

import java.io.Serializable;

import org.gatlin.util.DateUtil;

public class JieQiTips implements Serializable {

	private static final long serialVersionUID = -4331950199424568216L;

	private String jieqiid;
	private String name;
	private String pic;
	private String time;
	private Integer day;

	public String getJieqiid() {
		return jieqiid;
	}

	public String getName() {
		return name;
	}

	public String getPic() {
		return pic;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
	
	public void timeConvert() { 
		this.day = Integer.valueOf(DateUtil.convert(time, DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.yyyyMMdd));
	}
}
