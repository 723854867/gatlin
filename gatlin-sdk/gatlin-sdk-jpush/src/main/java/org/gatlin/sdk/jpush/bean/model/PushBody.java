package org.gatlin.sdk.jpush.bean.model;

import java.io.Serializable;

import org.gatlin.core.service.http.HttpPost.Body;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PushBody implements Body {

	private static final long serialVersionUID = -702566920001727190L;

	// 必填：推送平台设置
	private Object platform;
	// 必填：推送设备对象，表示一条推送可以被推送到哪些设备列表。确认推送设备对象，JPush 提供了多种方式，比如：别名、标签、注册ID、分群、广播等。
	private Object audience;
	// 可选：通知内容体。是被推送到客户端的内容。与 message 一起二者必须有其一，可以二者并存
	private Notification notification;
	// 可选：消息内容体。是被推送到客户端的内容。与 notification 一起二者必须有其一，可以二者并存
	private Message message;
	// 可选：短信渠道补充送达内容提
	@SerializedName("sms_message")
	private String smsMessage;
	// 可选：推送参数
	private Options options;
	// 可选：用于防止 api 调用端重试造成服务端的重复推送而定义的一个标识符
	private String cid;

	public PushBody platformAll() {
		this.platform = "all";
		return this;
	}
	
	public PushBody platform(Platform... platforms) {
		this.platform = platforms;
		return this;
	}
	
	public PushBody audienceAll() {
		this.audience = "all";
		return this;
	}
	
	public PushBody audience(Audience audience) {
		this.audience = audience;
		return this;
	}
	
	public PushBody notifaction(Notification notification) {
		this.notification = notification;
		return this;
	}
	
	public PushBody message(Message message) {
		this.message = message;
		return this;
	}
	
	public PushBody options(Options options) {
		this.options = options;
		return this;
	}
	
	public Options getOptions() {
		return options;
	}
}
