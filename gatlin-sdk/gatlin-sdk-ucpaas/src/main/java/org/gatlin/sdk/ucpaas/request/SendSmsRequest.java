package org.gatlin.sdk.ucpaas.request;

import org.gatlin.sdk.ucpaas.UcpassConfig;
import org.gatlin.sdk.ucpaas.response.SendSmsResponse;

public class SendSmsRequest extends UcpassRequest<SendSmsResponse, SendSmsRequest> {
	
	private SendSmsRequest(Builder body) {
		super(UcpassConfig.pathSendSms());
		this.body = body;
	}

	@SuppressWarnings("unused")
	public static class Builder extends org.gatlin.sdk.ucpaas.request.UcpassRequest.Builder {

		private static final long serialVersionUID = 681358946597472280L;

		// 模板中的替换参数，可以为空或者不传。多个字符串用英文逗号分隔，不能含有特殊符号
		private String param;
		// 手机号，仅支持国内号码
		private String mobile;
		// 用户透传ID
		private String uid;
		// 模板id
		private String templateid;
		
		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}
		
		public Builder mobile(String mobile) {
			this.mobile = mobile;
			return this;
		}
		
		public Builder templateId(String templateid) {
			this.templateid = templateid;
			return this;
		}
		
		public Builder params(String... params) {
			StringBuilder builder = new StringBuilder();
			for (String param : params)
				builder.append(param).append(",");
			this.param = builder.deleteCharAt(builder.length() - 1).toString();
			return this;
		}
		
		public SendSmsRequest build() {
			return new SendSmsRequest(this);
		}
	}
}
