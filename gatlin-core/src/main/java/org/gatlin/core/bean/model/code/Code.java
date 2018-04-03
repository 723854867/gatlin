package org.gatlin.core.bean.model.code;

import org.gatlin.core.bean.model.option.StrOption;

/**
 * 错误码
 * 
 * @author lynn
 */
public class Code extends StrOption {
	
	private static final long serialVersionUID = -8458025331329472294L;
	
	public Code() {}
	
	public Code(String key, String desc) {
		super(key, desc);
	}
}
