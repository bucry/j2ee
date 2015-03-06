package com.framework.core.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 为需要的数据提供格式转换
 */
public class DateFormatUtils {
	private static SimpleDateFormat second = new SimpleDateFormat(
			"yy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat day = new SimpleDateFormat(
	"yy-MM-dd");

	/**
	 * 格式化日期(精确到秒)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateSecond(Date date) {
		return second.format(date);
	}
	
	/**
	 * 格式化日期(精确到天)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateDay(Date date) {
		return day.format(date);
	}

	/**
	 * 将double类型的数字保留两位小数（四舍五入）
	 * 
	 * @param number
	 * @return
	 */
	public static String formatNumber(double number) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#0.00");
		return df.format(number);
	}
	
	/**
	 * 将字符串转换成日期
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date formateDate(String date)throws Exception {
		return day.parse(date);
	}
}
