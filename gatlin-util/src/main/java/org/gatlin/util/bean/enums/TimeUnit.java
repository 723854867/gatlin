package org.gatlin.util.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum TimeUnit implements IEnum {

	SECOND(1, 1000),
	MINUTE(2, 60000),
	HOUR(3, 3600000),
	DAY(4, 86400000),
	WEEK(5, 604800000),
	MONTH(6, 2592000000l),
	SEASON(7, 7776000000l),
	YEAR(8, 31104000000l);
	
	private int mark;
	private long millis;
	
	private TimeUnit(int mark, long millis) {
		this.mark = mark;
		this.millis = millis;
	}
	
	@Override
	public int mark() {
		return mark;
	}
	
	public long days() {
		if (millis < DAY.millis)
			throw new UnsupportedOperationException("Error invoke");
		return millis / DAY.millis;
	}
	
	public long millis() {
		return this.millis;
	}
	
	public long seconds() {
		return this.millis / 1000;
	}
	
	public static final TimeUnit match(int unit) {
		for (TimeUnit temp : TimeUnit.values()) {
			if (temp.mark == unit)
				return temp;
		}
		return null;
	}
}
