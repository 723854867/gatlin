package org.gatlin.sdk.jpush.bean.model;

public enum NotificationStyle {

	INBOX(2),
	BIG_TEXT(1),
	BIG_PICTURE(3);
	
	private int mark;
	
	private NotificationStyle(int mark) {
		this.mark = mark;
	}
	
	public int mark() {
		return mark;
	}
}
