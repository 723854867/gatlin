package org.gatlin.sdk.jpush.request;

import org.gatlin.sdk.jpush.bean.model.PushBody;
import org.gatlin.sdk.jpush.response.JPushResponse;

public class JPushVerify extends JiGuangRequest<JPushResponse, JPushVerify> {

	public JPushVerify(PushBody body) {
		super("v3/push/validate");
		this.body = body;
	}
}
