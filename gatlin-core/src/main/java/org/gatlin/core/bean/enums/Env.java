package org.gatlin.core.bean.enums;

public enum Env {

	// 本地
	LOCAL,

	// 测试
	TEST,

	// 生产
	ONLINE;

	public static final Env match(String env) {
		for (Env temp : Env.values()) {
			if (temp.name().equalsIgnoreCase(env))
				return temp;
		}
		return null;
	}
}
