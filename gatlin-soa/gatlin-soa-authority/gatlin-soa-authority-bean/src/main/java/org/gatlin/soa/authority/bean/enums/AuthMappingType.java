package org.gatlin.soa.authority.bean.enums;

import org.gatlin.util.bean.IEnum;

public enum AuthMappingType implements IEnum {
	
	// 模块api映射
	MODULAR_API(1),
	// 角色模块映射
	ROLE_MODULAR(2),
	// 用户角色映射
	USER_ROLE(3);

	private int mark;
	
	private AuthMappingType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int mark() {
		return mark;
	}
}
