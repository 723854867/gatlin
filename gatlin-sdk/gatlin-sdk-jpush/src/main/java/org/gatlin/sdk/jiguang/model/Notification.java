package org.gatlin.sdk.jiguang.model;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Notification implements Serializable {

	private static final long serialVersionUID = 5660787392994868490L;

	/**
	 * 必填
	 * 通知的内容在各个平台上，都可能只有这一个最基本的属性 "alert"。
	 * 这个位置的 "alert" 属性（直接在 notification 对象下），是一个快捷定义，各平台的 alert 信息如果都一样，则可不定义。如果各平台有定义，则覆盖这里的定义。
	 */
	private String alert;
	private IosNotification ios;
	private WPNotification winphone;
	private AndroidNotifaction android;
	
	public Notification alert(String alert) { 
		this.alert = alert;
		return this;
	}
	
	public Notification ios(IosNotification notifaction) { 
		this.ios = notifaction;
		return this;
	}
	
	public Notification winphone(WPNotification notifaction) { 
		this.winphone = notifaction;
		return this;
	}
	
	public Notification android(AndroidNotifaction notifaction) { 
		this.android = notifaction;
		return this;
	}
}
