package org.gatlin.util;

import java.math.BigDecimal;

public class ConversionAmountUtil {

	private static String chineseUnit = "元=十=百=千=万=十=百=千=亿=十=百=千=万";
	private static String chineseValue = "零壹贰叁肆伍陆柒捌玖";

	public static String getChinese(BigDecimal money) {
		double number = money.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String chinese = "";
		String decimalsChinese = "";
		String intNumber = "";
		String decimals = "";
		// 判断所传过来的值，是整数还是小数
		String srcNumber = number + "";// 把长整型转换为String 型

		intNumber = srcNumber.substring(0, srcNumber.indexOf("."));
		decimals = srcNumber.substring(srcNumber.indexOf(".") + 1, srcNumber.length());

		String chineseUnit1[] = chineseUnit.split("=");
		// 转换整数部分
		for (int i = 0; i < intNumber.length(); i++) {
			chinese += chineseValue.charAt(Integer.parseInt(srcNumber.charAt(i) + ""))
					+ chineseUnit1[intNumber.length() - 1 - i];
		}

		boolean isZero = Integer.parseInt(decimals.substring(0, 1)) == 0;
		// 这个地方的判断主要是因为，零钱有小数部分，小数部分的默认值是0.0,默认的有小数部分。所以需要判断
		// 小数部分那个小数是否为0，如果为0，就需要转换小数部分了。
		// 计算小数部分
		if (decimals.length() == 1 && isZero == false) {
			;
			decimalsChinese += chineseValue.charAt(Integer.parseInt(decimals.substring(0, 1))) + "角";
		} else if (decimals.length() == 2 && isZero == false) {
			decimalsChinese += chineseValue.charAt(Integer.parseInt(decimals.substring(0, 1))) + "角"
					+ chineseValue.charAt(Integer.parseInt(decimals.substring(1, 2))) + "分";
		}
		chinese += decimalsChinese;

		while (chinese.indexOf("零零") != -1 || chinese.indexOf("零万") != -1 || chinese.indexOf("零亿") != -1
				|| chinese.indexOf("亿万") != -1 || chinese.indexOf("零百") != -1 || chinese.indexOf("零元") != -1
				|| chinese.indexOf("零十") != -1 || chinese.indexOf("零千") != -1 || chinese.indexOf("零角") != -1) {
			chinese = chinese.replaceAll("零零", "零");
			chinese = chinese.replaceAll("零十", "零");
			chinese = chinese.replaceAll("零万", "万");
			chinese = chinese.replaceAll("零亿", "亿");
			chinese = chinese.replaceAll("零元", "元");
			chinese = chinese.replaceAll("亿万", "亿零");
			chinese = chinese.replaceAll("零百", "零");
			chinese = chinese.replaceAll("零千", "零");
			chinese = chinese.replaceAll("零角", "零");
			chinese = chinese.replaceAll("零([十百千])", "零");
		}
		return chinese;

	}
}
