package org.gatlin.web.util;

import org.gatlin.core.bean.model.code.Code;

public interface WebCode {

	final Code UNSUPPORT_CONTENT_TYPE 			= new Code("code.unsupport.content.type", "不支持的 ContentType");
	final Code UNSUPPORT_HTTP_METHOD 			= new Code("code.unsupport.http.method", "不支持的HTTP请求方法");
	final Code UPLOAD_SIZE_EXCEEDED 			= new Code("code.upload.size.exceeded", "上传文件太大");
	final Code UPLOAD_COUNT_EXCEEDED 			= new Code("code.upload.count.exceeded", "上传文件数量超过限制");
	final Code UPLOAD_FAILURE		 			= new Code("code.upload.failure", "资源上传失败");

	final Code API_MAINTENANCE 					= new Code("code.api.maintenance", "接口维护中");
	final Code LOG_REQUEST_UNSUPPORT 			= new Code("code.log.request.unsupport", "未开启访问日志服务");
}
