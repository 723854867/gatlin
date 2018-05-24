package org.gatlin.sdk.jiguang.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WPNotification implements Serializable {

	private static final long serialVersionUID = 6720855155767270779L;

	// 必填：通知内容。会填充到 toast 类型 text2 字段上。这里指定了，将会覆盖上级统一指定的 alert 信息；内容为空则不展示到通知栏。
	private String alert;
	// 可选：通知标题。会填充到 toast 类型 text1 字段上。
	private String title;
	// 可选：点击打开的页面名称。点击打开的页面。会填充到推送信息的 param 字段上，表示由哪个 App 页面打开该通知。可不填，则由默认的首页打开。
	@SerializedName("_open_page")
	private String openPage;
	// 可选：扩展字段
	private Object extras;
	
	public WPNotification alert(String alert) { 
		this.alert = alert;
		return this;
	}
	
	public WPNotification titile(String title) {
		this.title = title;
		return this;
	}
	
	public WPNotification openPage(String openPage) {
		this.openPage = openPage;
		return this;
	}
	
	public WPNotification extras(Object extras) {
		this.extras = extras;
		return this;
	}
}
