package org.gatlin.core.bean.model.message;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.code.Code;
import org.gatlin.util.lang.StringUtil;

public class Response<T> implements Message {

	private static final long serialVersionUID = 4618491061991821038L;

	// 响应结果
	private T attach;
	// 错误码
	private String code;
	private String desc = "";
	
	public Response() {}
	
	public Response(T attach) {
		this(CoreCode.SUCCESS, attach);
	}
	
	public Response(Code code) {
		this(code, null);
	}
	
	public Response(Code code, T attach) {
		this.attach = attach;
		this.code = code.key();
		this.desc = code.defaultValue();
	}

	public T getAttach() {
		return attach;
	}

	public void setAttach(T attach) {
		this.attach = attach;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void appendDesc(String desc) {
		this.desc += StringUtil.hasText(this.desc) ? " - [" + desc + "]" : desc;
	}
	
	public static <T> Response<T> ok() {
		return new Response<T>(CoreCode.SUCCESS);
	}
	
	public static <T> Response<T> error(Code code) {
		return new Response<T>(code);
	}
}
