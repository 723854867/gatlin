package org.gatlin.sdk.ucpaas.response;

import com.google.gson.annotations.SerializedName;

public class CreateSmsTemplateResponse extends UcpassResponse {

	private static final long serialVersionUID = -1596164999361999028L;

	private String templateid;
	// 创建时间,格式YYYY-MM-DD hh:mm:ss
	@SerializedName("create_date")
	private String createDate;
	
	public String getTemplateid() {
		return templateid;
	}
	
	public String getCreateDate() {
		return createDate;
	}
}
