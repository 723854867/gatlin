package org.gatlin.sdk.jiguang.request;

import org.gatlin.sdk.jiguang.JiGuangConfig;
import org.gatlin.sdk.jiguang.model.PushBody;
import org.gatlin.sdk.jiguang.response.JiGuangResponse;

public class Jpush extends JiGuangRequest<JiGuangResponse, Jpush> {

	public Jpush(PushBody body) {
		super(JiGuangConfig.pathPush());
		this.body = body;
	}
}
