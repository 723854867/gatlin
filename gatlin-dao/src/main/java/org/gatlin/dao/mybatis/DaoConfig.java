package org.gatlin.dao.mybatis;

import java.util.Properties;

import org.gatlin.util.lang.StringUtil;

public class DaoConfig {

	// 处理关键字，默认空mysql为 `{0}`;sqlserver 为 [{0}]
	private String keyWordWrapper = "`{0}`";
	// 默认为驼峰转下划线
	private Style nameStyle = Style.camelhump;

	public String getKeyWordWrapper() {
		return keyWordWrapper;
	}

	public void setKeyWordWrapper(String keyWordWrapper) {
		this.keyWordWrapper = keyWordWrapper;
	}

	public Style getNameStyle() {
		return nameStyle;
	}
	
	public void setNameStyle(Style nameStyle) {
		this.nameStyle = nameStyle;
	}

	public void setProperties(Properties properties) {
		if (properties == null) 
			return;
		String style = properties.getProperty("db.mybatis.nameStyle");
		if (StringUtil.hasText(style)) 
			this.nameStyle = Style.valueOf(style);
		String keyWordWrapper = properties.getProperty("db.mybatis.keyWordWrapper");
		if (StringUtil.hasText(keyWordWrapper)) 
			this.keyWordWrapper = keyWordWrapper;
	}

	/**
	 * 列名和表名转换风格
	 */
	public enum Style {
		// 原值
		normal,
		// 驼峰转下划线
		camelhump,
		// 转换为大写
		uppercase,
		// 转换为小写
		lowercase,
		// 驼峰转下划线大写形式
		camelhumpAndUppercase,
		// 驼峰转下划线小写形式
		camelhumpAndLowercase;
	}
}
