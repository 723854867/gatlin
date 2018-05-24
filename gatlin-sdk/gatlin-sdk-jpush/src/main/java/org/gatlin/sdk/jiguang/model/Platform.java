package org.gatlin.sdk.jiguang.model;

public enum Platform {

	// 需要在 options 中通过 apns_production 字段来设定推送环境,True 表示推送生产环境，False 表示要推送开发环境； 如果不指定则为推送生产环境
	ios,
	// 推送到所有平台
	all,
	android,
	winphone;
}
