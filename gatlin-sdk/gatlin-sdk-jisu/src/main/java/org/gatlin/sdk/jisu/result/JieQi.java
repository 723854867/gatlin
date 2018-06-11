package org.gatlin.sdk.jisu.result;

import java.io.Serializable;
import java.util.List;

import org.gatlin.sdk.jisu.bean.model.JieQiNow;
import org.gatlin.sdk.jisu.bean.model.JieQiTips;
import org.gatlin.util.DateUtil;

public class JieQi implements Serializable {

	private static final long serialVersionUID = 8395181967717896645L;

	private String song;
	private JieQiNow now;
	private List<JieQiTips> list;
	
	public String getSong() {
		return song;
	}
	
	public JieQiNow getNow() {
		return now;
	}
	
	public List<JieQiTips> getList() {
		return list;
	}
	
	public void timeConvert() {
		this.now.setDay(Integer.valueOf(DateUtil.convert(now.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.yyyyMMdd)));
		list.forEach(item -> item.timeConvert());
		list.sort((o1, o2) -> o1.getDay() - o2.getDay());
	}
}
