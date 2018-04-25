package org.gatlin.sdk.jiguang.model;

import java.io.Serializable;

import org.gatlin.core.util.Assert;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AndroidNotifaction implements Serializable {

	private static final long serialVersionUID = 4316135615453476284L;

	// 必填：这里指定了，则会覆盖上级统一指定的 alert 信息；内容可以为空字符串，则表示不展示到通知栏。
	private String alert;
	// 可选：通知标题，如果指定了，则通知里原来展示 App名称的地方，将展示成这个字段
	private String title;
	// 可选：通知栏样式ID，Android SDK 可设置通知栏样式，这里根据样式 ID 来指定该使用哪套样式。
	@SerializedName("builder_id")
	private int builderId;
	// 可选：通知栏暂时优先级，默认为0，范围为 -2～2 ，其他值将会被忽略而采用默认。
	private int priority;
	// 可选：通知栏条目过滤或排序，完全依赖 rom 厂商对 category 的处理策略
	private String category;
	// 可选：通知栏样式类型。默认为0，还有1，2，3可选，用来指定选择哪种通知栏样式，其他值无效。有三种可选分别为bigText=1，Inbox=2，bigPicture=3。
	private int style = 0;
	// 可选：通知提醒方式。可选范围为 -1 ～ 7 ，默认为-1，所有方式都有：声音为1，震动为2，灯光为4.可以使用任意组合，位或操作
	@SerializedName("alert_type")
	private int alertType = -1;
	// 可选：当文本通知栏样式。当 style = 1 时可用，内容会被通知栏以大文本的形式展示出来。支持 api 16以上的rom。
	@SerializedName("big_text")
	private String bigText;
	// 可选：文本条目通知栏样式。当 style = 2 时可用， json 的每个 key 对应的 value 会被当作文本条目逐条展示。支持 api 16以上的rom。
	private Object inbox;
	// 可选：大图片通知栏样式。当 style = 3 时可用，可以是网络图片 url，或本地图片的 path，目前支持.jpg和.png后缀的图片。图片内容会被通知栏以大图片的形式展示出来。如果是 http／https 的url，会自动下载；如果要指定开发者准备的本地图片就填sdcard 的相对路径。支持 api 16以上的rom。
	@SerializedName("big_pic_path")
	private String bigPicPath;
	// 可选：自动以json格式的扩展字段
	private Object extras;
	
	public AndroidNotifaction alert(String alert) { 
		this.alert = alert;
		return this;
	}
	
	public AndroidNotifaction title(String title) { 
		this.title = title;
		return this;
	}
	
	public AndroidNotifaction builderId(int builderId) { 
		this.builderId = builderId;
		return this;
	}
	
	public AndroidNotifaction priority(int priority) { 
		Assert.isTrue(priority >= -2 && priority <= 2, "priority must between [-2 ~ 2]");
		this.priority = priority;
		return this;
	}
	
	public AndroidNotifaction category(String category) { 
		this.category = category;
		return this;
	}
	
	public AndroidNotifaction style(NotificationStyle style) { 
		this.style = style.mark();
		return this;
	}
	
	public AndroidNotifaction alertType(AlertType... types) { 
		int type = 0;
		for (AlertType temp : types)
			type |= temp.mark();
		this.alertType = type;
		return this;
	}
	
	public AndroidNotifaction bigText(String text) { 
		this.bigText = text;
		return this;
	}
	
	public AndroidNotifaction inbox(Object inbox) { 
		this.inbox = inbox;
		return this;
	}
	
	public AndroidNotifaction bigPicPath(String bigPicPath) { 
		this.bigPicPath = bigPicPath;
		return this;
	}
	
	public AndroidNotifaction extras(String extras) { 
		this.extras = extras;
		return this;
	}
}
