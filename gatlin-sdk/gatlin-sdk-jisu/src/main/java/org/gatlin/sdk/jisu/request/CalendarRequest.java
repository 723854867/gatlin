package org.gatlin.sdk.jisu.request;

import org.gatlin.sdk.jisu.JisuConfig;
import org.gatlin.sdk.jisu.result.Calendar;

public class CalendarRequest extends JisuRequet<Calendar, CalendarRequest> {

	public CalendarRequest() {
		super("/calendar/query", JisuConfig.WAN_NIAN_LI);
	}
	
	// 格式 YYYY-MM-DD
	public CalendarRequest date(String date) {
		this.params.put("date", date);
		return this;
	}
}
