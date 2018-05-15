package org.gatlin.skd.chuanglan.response;

import org.gatlin.core.service.http.HttpResponse;
import org.gatlin.skd.chuanglan.bean.ChuangLanException;
import org.gatlin.skd.chuanglan.bean.Code;

public class ChuangLanResponse implements HttpResponse {

	private static final long serialVersionUID = 4798392469296741149L;
	
	// 错误码
	private String code;
	// 响应时间
	private String time;
	// 失败状态码说明（成功返回空）
	private String errorMsg;
	
	public String getTime() {
		return time;
	}
	
	@Override
	public String code() {
		return this.code;
	}

	@Override
	public String desc() {
		return this.errorMsg;
	}
	
	@Override
	public void verify() {
		Code code = Code.match(this.code);
		if (code != Code.SUCCESS)
			throw new ChuangLanException(code.mark(), errorMsg);
	}
}
