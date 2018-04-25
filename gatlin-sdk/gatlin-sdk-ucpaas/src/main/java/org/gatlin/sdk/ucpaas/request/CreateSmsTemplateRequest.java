package org.gatlin.sdk.ucpaas.request;

import org.gatlin.sdk.ucpaas.UcpassConfig;
import org.gatlin.sdk.ucpaas.model.SmsType;
import org.gatlin.sdk.ucpaas.response.CreateSmsTemplateResponse;

import com.google.gson.annotations.SerializedName;

/**
 * 创建短信模板
 * 
 * @author lynn
 */
public class CreateSmsTemplateRequest extends UcpassRequest<CreateSmsTemplateResponse, CreateSmsTemplateRequest> {

	private CreateSmsTemplateRequest(Builder body) {
		super(UcpassConfig.pathCreateSmsTemplate());
		this.body = body;
	}
	
	@SuppressWarnings("unused")
	public static class Builder extends org.gatlin.sdk.ucpaas.request.UcpassRequest.Builder {

		private static final long serialVersionUID = 681358946597472280L;

		// 短信类型：0:通知短信、5:会员服务短信（需企业认证）、4:验证码短信(此类型content内必须至少有一个参数{1})
		private String type;
		// 短信模板名称，限6个汉字或20个数字、英文字、符号
		@SerializedName("template_name")
		private String templateName;
		// 短信签名，建议使用公司名/APP名/网站名，限2-12个汉字、英文字母和数字，不能纯数字
		private String autograph;
		// 短信内容，最长500字，不得含有【】符号，可支持输入参数，参数示例"{1}"、"{2}"
		private String content;
		
		public Builder type(SmsType type) {
			this.type = type.mark();
			return this;
		}
		
		public Builder name(String name) {
			this.templateName = name;
			return this;
		}
		
		public Builder autograph(String autograph) {
			this.autograph = autograph;
			return this;
		}
		
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		
		public CreateSmsTemplateRequest build() {
			return new CreateSmsTemplateRequest(this);
		}
	}
}
