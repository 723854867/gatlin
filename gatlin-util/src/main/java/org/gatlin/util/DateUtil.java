package org.gatlin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	
	public static final int MINITUE_MILLS				= 60000;
	public static final int DAY_SECONDS					= 86400;
	public static final int DAY_MILLS					= 86400000;
	public static final int HOUR_SECONDS				= 3600;
	public static final int HOUR_MILLS					= 3600000;
	public static final int HALF_HOUR_MILLS				= 1800000;
	public static final int DAYS_OF_MONTH				= 30;
	public static final int DAYS_OF_YEAR				= 365;
	public static final int MONTHS_OF_YEAR				= 12;
	
	public static final String ISO8601_UTC 				= "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String ISO8601_UTC_S 			= "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String HHmmss					= "HHmmss";
	public static final String HH_mm_ss					= "HH:mm:ss";
	public static final String yyyyMMdd					= "yyyyMMdd";
	public static final String yyyy$MM$dd				= "yyyy/MM/dd";
	public static final String yyyy_MM_dd				= "yyyy-MM-dd";
	public static final String yyyyMMddHHmmss			= "yyyyMMddHHmmss";
	public static final String YYYY_MM_DD_HH_MM_SS		= "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS_SSS	= "yyyy-MM-dd HH:mm:ss.SSS";

	public static final TimeZone TIMEZONE_UTC			= TimeZone.getTimeZone("UTC");
	public static final TimeZone TIMEZONE_GMT_8			= TimeZone.getTimeZone("GMT+8:00");
	
	// ************* 获取时间戳   *************

	public static int current() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static final long iso8601Timestamp(String date) {
		return getTime(date, ISO8601_UTC_S, TIMEZONE_UTC);
	}
	
	public static final long getTime(Object date, String format) { 
		return getTime(date, format, TIMEZONE_GMT_8);
	}
	
	public static final long getTime(Object date, String format, TimeZone zone) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(zone);
		try {
			return df.parse(date.toString()).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}
	
	public static final int getTimeSeconds(Date date) {
		int time =  (int) (date.getTime() / 1000);
		return time < 0 ? 0 : time;
	}
	
	// ************* 获取指定格式的时间   *************
	
	/**
	 * iso8601 格林威治时间
	 */
	public static final String iso8601UTCDate() { 
		return getDate(ISO8601_UTC, System.currentTimeMillis(), TIMEZONE_UTC);
	}
	
	/**
	 * 由于iso8601只精确到秒，因此这里做了一个变种，该方法的格式类似：2017-10-12T10:01:46.818Z
	 */
	public static final String iso8601UTCMillisDate() {
		return getDate(ISO8601_UTC_S, System.currentTimeMillis(), TIMEZONE_UTC);
	}
	
	public static final String getDate(String format) {
		return getDate(format, System.currentTimeMillis());
	}
	
	public static final String getDate(Date date, String format) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TIMEZONE_GMT_8);
		return df.format(date);
	}
	
	public static final String getDate(String format, long timestamp) {
		return getDate(format, timestamp, TIMEZONE_GMT_8);
	}
	
	public static final String getDate(Date date, String format, TimeZone timeZone) { 
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(timeZone);
		return df.format(date);
	}
	
	public static final String getDate(String format, long timestamp, TimeZone timeZone) {
		DateFormat df = new SimpleDateFormat(format.toString());
		df.setTimeZone(timeZone);
		return df.format(new Date(timestamp));
	}
	
	public static final String getDate(Object date, String format, String toFormat) {
		long time = getTime(date, format, TIMEZONE_GMT_8);
		return getDate(toFormat, time);
	}
	
	public static final String getDate(Object date, String format, String toFormat, TimeZone timeZone) {
		long time = getTime(date, format, timeZone);
		return getDate(toFormat, time);
	}
	
	// ************* 时间格式转换   *************
	
	public static final String convert(String source, String sf, String tf) {
		return convert(source, sf, tf, TIMEZONE_GMT_8);
	}
	
	public static final String convert(String source, String sf, String tf, TimeZone timeZone) {
		long time = getTime(source, sf, timeZone);
		return getDate(tf, time);
	}
	
	// ************* 加时间   *************
	
	public static final String add(String format, TimeUnit unit, long duration) {
		return add(format, unit, duration, TIMEZONE_GMT_8);
	}
	
	public static final String add(String format, TimeUnit unit, long duration, TimeZone timeZone) {
		long timestamp = System.currentTimeMillis();
		timestamp += unit.toMillis(duration);
		return getDate(format, timestamp);
	}
	
	public static final String add(Object source, String format, TimeUnit unit, long duration) {
		return add(source, format, format, unit, duration, TIMEZONE_GMT_8);
	}
	
	public static final String add(Object source, String sf, String tf, TimeUnit unit, long duration) {
		return add(source, sf, tf, unit, duration, TIMEZONE_GMT_8);
	}
	
	public static final String add(Object source, String format, TimeUnit unit, long duration, TimeZone timeZone) {
		return add(source, format,format, unit, duration, timeZone);
	}
	
	public static final String add(Object source, String sf, String tf, TimeUnit unit, long duration, TimeZone timeZone) {
		long timestamp = getTime(source, sf, timeZone);
		timestamp += unit.toMillis(duration);
		return getDate(tf, timestamp);
	}
	
	// ************* 减时间   ************* 
	
	public static final String substract(String format, TimeUnit unit, long duration) {
		return substract(format, unit, duration, TIMEZONE_GMT_8);
	}
	
	public static final String substract(String format, TimeUnit unit, long duration, TimeZone timeZone) {
		long timestamp = System.currentTimeMillis();
		timestamp -= unit.toMillis(duration);
		return getDate(format, timestamp);
	}
	
	public static final String substract(Object source, String format, TimeUnit unit, long duration) {
		return substract(source, format, format, unit, duration, TIMEZONE_GMT_8);
	}
	
	public static final String substract(Object source, String sf, String tf, TimeUnit unit, long duration) {
		return substract(source, sf, tf, unit, duration, TIMEZONE_GMT_8);
	}
	
	public static final String substract(Object source, String format, TimeUnit unit, long duration, TimeZone timeZone) {
		return substract(source, format,format, unit, duration, timeZone);
	}
	
	public static final String substract(Object source, String sf, String tf, TimeUnit unit, long duration, TimeZone timeZone) {
		long timestamp = getTime(source, sf, timeZone);
		timestamp -= unit.toMillis(duration);
		return getDate(tf, timestamp);
	}
	
	// ************* 获取零点时间  *************
	
	public static final Date zeroDate() { 
		return zeroDate(new Date());
	}
	
	public static final Date zeroDate(Date date) { 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static final long zeroTime() {
		return zeroTime(new Date());
	}
	
	public static final long zeroTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static final int zeroTimeInSecond() {
		return zeroTimeInSecond(new Date());
	}
	
	public static final int zeroTimeInSecond(Date date) {
		return (int) (zeroTime(date) / 1000);
	}
	
	public static final Date nextZeroDate() { 
		return nextZeroDate(new Date());
	}
	
	public static final Date nextZeroDate(Date date) { 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static final long nextZeroTime() {
		return zeroTime(new Date());
	}
	
	public static final long nextZeroTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTimeInMillis();
	}
	
	public static final int nextZeroTimeInSecond() {
		return nextZeroTimeInSecond(new Date());
	}
	
	public static final int nextZeroTimeInSecond(Date date) {
		return (int) (nextZeroTime(date) / 1000);
	}
	
	// ************* 计算两个时间之间的相隔  *************
	
	/**
	 * 获取当前时间到指定时间之间的差值：当前时间精确到毫秒
	 */
	public static final long interval(Object date2, String format2, org.gatlin.util.bean.enums.TimeUnit unit) {
		return interval(getDate(DateUtil.yyyyMMddHHmmss), DateUtil.yyyyMMddHHmmss, date2, format2, TIMEZONE_GMT_8, unit);
	}
	
	public static final long interval(Object date1, String format1, Object date2, String format2, org.gatlin.util.bean.enums.TimeUnit unit) {
		return interval(date1, format1, date2, format2, TIMEZONE_GMT_8, unit);
	}
	
	/**
	 * 计算 date1 和 date2 之间的时间差:如果 剩余时间不够一个周期则算一个时间差
	 * 如果 date1 小于 date2 则返回正值，否则返回负值
	 */
	public static final long interval(Object date1, String format1, Object date2, String format2, TimeZone timeZone, org.gatlin.util.bean.enums.TimeUnit unit) {
		long time1 = getTime(date1.toString(), format1, timeZone);
		long time2 = getTime(date2.toString(), format2, timeZone);
		long interval = Math.abs(time1 - time2);
		long mod = interval % unit.millis();
		interval = 0 == mod ? interval / unit.millis() : interval / unit.millis() + 1;
		return time1 < time2 ? interval : -interval;
	}
	
	public static final int monthDays() {
		return monthDays(System.currentTimeMillis());
	}
	
	public static final int monthDays(Object date, String format) {
		return monthDays(getTime(date, format));
	}
	
	/**
	 * 获取指定月份的天数
	 */
	public static final int monthDays(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, false);
		return calendar.get(Calendar.DATE);
	}
	
	// 当月最后一毫秒
	public static final long lastTimeOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
	}
	
	// 当月第一毫秒
	public static final long firstTimeOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
	}
}
