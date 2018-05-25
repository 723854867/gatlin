package org.gatlin.sdk.jpush.request;

import org.gatlin.sdk.jpush.bean.model.PushBody;
import org.gatlin.sdk.jpush.response.JPushResponse;

public class Jpush extends JiGuangRequest<JPushResponse, Jpush> {

	public Jpush(PushBody body) {
		super("v3/push");
		this.body = body;
	}
}
