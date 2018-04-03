package org.gatlin.util.lang;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static Random UUID;
	
	public static final String EMPTY = "";
	public static final String UNDERLINE = "_";
	
	/**
	 * 驼峰表示法
	 */
	public static final Pattern CAMEL = Pattern.compile("[A-Z]([a-z\\d]+)?");
	
	static {
		SecureRandom secureRandom = new SecureRandom();
		byte[] seed = new byte[8];
		secureRandom.nextBytes(seed);
		UUID = new Random(new BigInteger(seed).longValue());
	}
	
	public static final String uuid() { 
		byte[] randomBytes = new byte[16];
		UUID.nextBytes(randomBytes);
		long mostSigBits = 0;
		for (int i = 0; i < 8; i++) 
			mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
		long leastSigBits = 0;
		for (int i = 8; i < 16; i++)
			leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
		return new UUID(mostSigBits, leastSigBits).toString();
	}
	
	public static final boolean hasText(CharSequence str) {
		if (!hasLength(str))
			return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i)))
				return true;
		}
		return false;
	}
	
	public static final  boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	public static final String trimIncludeNull(String content) {
		return !hasText(content) ? EMPTY : content.trim();
	}
	
	/**
	 * 替换字符串
	 * 
	 * @param string
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String replace(String string, String oldPattern, String newPattern) {
		if (!hasLength(string) || !hasLength(oldPattern) || newPattern == null)
			return string;
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = string.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(string.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = string.indexOf(oldPattern, pos);
		}
		sb.append(string.substring(pos));
		return sb.toString();
	}
	
	/**
	 * 驼峰转下划线
	 * 
	 * @param line
	 * @return
	 */
	public static final String camel2Underline(String line){
		if(!StringUtil.hasText(line))
        	return line;
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuilder builder = new StringBuilder();
        Matcher matcher = CAMEL.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            builder.append(word.toLowerCase());
            builder.append(matcher.end() == line.length() ? StringUtil.EMPTY : UNDERLINE);
        }
        return builder.toString();
    }
	
	/**
	 * 脱敏
	 * 
	 * @param identity
	 * @param top
	 * @param tail
	 * @return
	 */
	public static final String mask(String content, int top, int tail) {
		int len = content.length() - top - tail;
		if (len <= 0)
			return content;
		StringBuilder builder = new StringBuilder();
		int index = 0;
		while (top -- > 0) 
			builder.append(content.charAt(index++));
		while (len-- > 0) {
			builder.append("*");
			index++;
		}
		while (tail-- > 0)
			builder.append(content.charAt(index++));
		return builder.toString();
	}
	
	public static final String link(String seperator, Object... parts) {
		int len = seperator.length();
		StringBuilder builder = new StringBuilder();
		for (Object object : parts)
			builder.append(object.toString()).append(seperator);
		builder.deleteCharAt(builder.length() - len);
		return builder.toString();
	}
	
	/**
	 * 把set根据split分割组装成string
	 * @param set
	 * @param split
	 * @return
	 */
	public static String collectionToString(Collection<?> set,String split) {
		String value = "";
		for(Object object : set) {
			value+=object+split;
		}
		return value.substring(0,value.length()-1);
	}
	
	/**
	 * 匹配数字或带小数的数字
	 * @param str
	 * @return
	 */
	public static boolean isNumberOrPoint(String str) {
		Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
        Matcher isNumP = pattern.matcher(str);
        if( !isNumP.matches() ){
            return false;
        }
        return true;
	}
}
