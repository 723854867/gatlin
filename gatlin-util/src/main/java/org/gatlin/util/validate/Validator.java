package org.gatlin.util.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	/**
	 * 10位 unix 时间戳
	 */
	private static final Pattern UNIX_TIMESTAMP = Pattern.compile("[1-9]\\d{9}");
	
	/**
     * 正则表达式：验证邮箱
     */
    public static final Pattern REGEX_EMAIL = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");

	/**
	 * 正则表达式：验证身份证
	 */
	private static final Pattern IDENTITY = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

	/**
	 * 正则表达式：车牌号
	 */
	private static final Pattern VEHICLE_LICENSE = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");
	/**
	 * 车架号
	 */
	private static final Pattern VEHICLE_VIN = Pattern.compile("^[a-zA-Z0-9]{17}$");
	/**
	 * 发动机号简易验证
	 */
	private static final Pattern VEHICLE_ENGINE = Pattern.compile("^[a-zA-Z0-9]{4,}$");
	
	private static final Pattern INTEGER = Pattern.compile("^[-\\+]?[\\d]*$");						// 整数
	private static final Pattern POSITIVE_INTEGER = Pattern.compile("^[\\d]*$");					// 正整数
	private static final Pattern NUMBER = Pattern.compile("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");	// 数值
	
	/**
	 * 数字或者字母或者中文
	 */
	private static final Pattern LETTER_DIGIT_CHINESE = Pattern.compile("^[a-z0-9A-Z\u4e00-\u9fa5]+$");
	
	 /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return _matches(REGEX_EMAIL, email);
    }
    
	public static boolean isUnixTimestamp(String value) {
		return _matches(UNIX_TIMESTAMP, value);
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIdentity(String identity) {
		return _matches(IDENTITY, identity);
	}
	
	/**
	 * 校验车牌号
	 * 
	 * @param license
	 * @return
	 */
	public static boolean isVehicleLisense(String input) {
		return _matches(VEHICLE_LICENSE, input);
	}
	
	public static boolean isVehicleVin(String input) {
		return _matches(VEHICLE_VIN, input);
	}
	
	public static boolean isVehicleEngine(String input) {
		return _matches(VEHICLE_ENGINE, input);
	}
	
	public static boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	public static boolean isNumber(String input) {
		return _matches(NUMBER, input);
	}
	
	public static boolean isInteger(String input) {
		return _matches(INTEGER, input);
	}
	
	public static boolean isPositiveInteger(String input) {
		return _matches(POSITIVE_INTEGER, input);
	}

	/**
	 * 因为不能有特殊字符，因此负数不能识别
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isLetterDigitOrChinese(String input) {
		return _matches(LETTER_DIGIT_CHINESE, input);
	}
	
	private static boolean _matches(Pattern pattern, CharSequence input) {
		if (null == input)
			return false;
		Matcher m = pattern.matcher(input);
		return m.matches();
	}
}
