package org.gatlin.core.util;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.model.code.Code;
import org.gatlin.util.lang.StringUtil;

public class Assert {

	public static final <T> T  notNull(T param) {
		return notNull("notNull assert failure!", param);
	}
	
	public static final <T> T notNull(String desc, T param) {
		if (null == param)
			throw new CodeException(desc);
		return param;
	}
	
	public static final <T> T notNull(Code code, T param) {
		if (null == param)
			throw new CodeException(code);
		return param;
	}
	
	public static final boolean isTrue(boolean expression) { 
		return isTrue(expression, "isTrue asset failure!");
	}
	
	public static final boolean isTrue(boolean expression, String desc) { 
		if (!expression)
			throw new CodeException(desc);
		return expression;
	}
	
	public static final boolean isTrue(boolean expression, Code code) { 
		if (!expression)
			throw new CodeException(code);
		return expression;
	}
	
	public static final String hasText(String content, Code code) {
		if (!StringUtil.hasText(content))
			throw new CodeException(code);
		return content;
	}
}
