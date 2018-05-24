package org.gatlin.sdk.jiguang.model;

public enum AlertType {

	SOUND(1),
	LIGHTS(4),
	VIBRATE(2);
	
	private int mark;
	
	private AlertType(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
