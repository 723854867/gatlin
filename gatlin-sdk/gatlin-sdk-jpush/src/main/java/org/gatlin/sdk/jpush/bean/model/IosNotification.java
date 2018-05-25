package org.gatlin.sdk.jpush.bean.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class IosNotification implements Serializable {

	private static final long serialVersionUID = 7105550920373525432L;

	// 必填：这里指定内容将会覆盖上级统一指定的 alert 信息；内容为空则不展示到通知栏。支持字符串形式也支持官方定义的alert payload 结构
	private Object alert;
	// 可选：通知提示声音。如果无此字段，则此消息无声音提示；有此字段，如果找到了指定的声音就播放该声音，否则播放默认声音,如果此字段为空字符串，iOS 7 为默认声音，iOS 8及以上系统为无声音。
	private String sound = "";
	// 可选：应用角标，如果不填，表示不改变角标数字；否则把角标数字改为指定的数字；为 0 表示清除。JPush 官方 API Library(SDK) 会默认填充badge值为"+1",详情参考：badge +1
	private Integer badge = +1;
	// 可选：推送唤醒。推送的时候携带"content-available":true 说明是 Background Remote Notification，如果不携带此字段则是普通的Remote Notification。详情参考：Background Remote Notification
	@SerializedName("content-available")
	private Boolean contentAvailable;
	// 可选：推送的时候携带”mutable-content":true 说明是支持iOS10的UNNotificationServiceExtension，如果不携带此字段则是普通的Remote Notification。详情参考：UNNotificationServiceExtension
	@SerializedName("mutable-content")
	private Boolean mutableContent;
	// 可选：IOS8才支持。设置APNs payload中的"category"字段值
	private String category;
	// 可选：附加字段
	private Object extras;
	
	public IosNotification alert(Object alert) {
		this.alert = alert;
		return this;
	}
	
	public IosNotification alert(String sound) {
		this.sound = sound;
		return this;
	}
	
	public IosNotification alert(int badge) {
		this.badge = badge;
		return this;
	}
	
	public IosNotification contentAvaliable() {
		this.contentAvailable = true;
		return this;
	}
	
	public IosNotification mutableContent() {
		this.mutableContent = true;
		return this;
	}
	
	public IosNotification category(String category) {
		this.category = category;
		return this;
	}
	
	public IosNotification extras(Object extras) {
		this.extras = extras;
		return this;
	}
}
