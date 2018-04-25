package org.gatlin.sdk.jiguang.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * 应用内消息。或者称作：自定义消息，透传消息。
 * 此部分内容不会展示到通知栏上，JPush SDK 收到消息内容后透传给 App。需要 App 自行处理。
 * iOS 平台上，此部分内容在推送应用内消息通道（非APNS）获取。Windows Phone 暂时不支持应用内消息。
 * 
 * @author lynn
 */
@SuppressWarnings("unused")
public class Message implements Serializable {

	private static final long serialVersionUID = -702566920001727190L;

	// 必填：消息内容本身
	@SerializedName("msg_content")
	private String content;
	// 可选：消息标题
	private String title;
	// 可选：消息内容类型
	@SerializedName("content_type")
	private String contentType;
	// 可选
	private Object extras;
	
	public Message content(String content) { 
		this.content = content;
		return this;
	}
	
	public Message title(String title) { 
		this.title = title;
		return this;
	}
	
	public Message contentType(String contentType) { 
		this.contentType = contentType;
		return this;
	}
	
	public Message extras(Object extras) { 
		this.extras = extras;
		return this;
	}
}
