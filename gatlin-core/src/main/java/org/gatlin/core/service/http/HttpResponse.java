package org.gatlin.core.service.http;

import java.io.Serializable;

public interface HttpResponse extends Serializable {

	/**
	 * 请求错误码
	 */
	String code();
	
	/**
	 * 请求错误描述
	 */
	String desc();
	
	/**
	 * 验证
	 */
	void verify();
}
