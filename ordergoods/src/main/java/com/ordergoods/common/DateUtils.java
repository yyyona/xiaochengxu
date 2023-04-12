package com.ordergoods.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 日期处理类
 */
public class DateUtils {
	    private final static  Logger log = LoggerFactory.getLogger(DateUtils.class);

	    public final static String yyyy_MM = "yyyy-MM";
	 	public final static String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
		public final static String yyyy_MM_dd = "yyyy-MM-dd";
		public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
		private static String defaultDatePattern = yyyy_MM_dd_HH_mm_ss;


	/**
	 * 从date获取LocalDateTime
	 * @param date
	 * @return
	 */
		public static  LocalDateTime fromDate(Date date){
			Instant instant = date.toInstant();
			LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			return dateTime;

		}

		/**
		 * LocalDateTime 解析
		 * @param date
		 * @param pattern
		 * @return LocalDateTime
		 */
		public static LocalDateTime parseLocalDateTime(String date,String pattern){
			if(StringUtils.isEmpty(pattern)){
				pattern=defaultDatePattern;
			}
			if(StringUtils.isEmpty(date)){
				return null;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			//格式化
			LocalDateTime parse = LocalDateTime.parse(date,formatter);
			return parse;

		}

	/**
	 * 比较两个LocalDateTime之间天数，不足一天算一天，多于半天算多于一天
	 * @param t1
	 * @param t2
	 * @return
	 */
		public static  long diffDaysOfLocalDateTime(LocalDateTime t1,LocalDateTime t2){
			int i = t1.compareTo(t2);
			long days = i >= 0 ? (t1.toLocalDate().toEpochDay() - t2.toLocalDate().toEpochDay()) : (t2.toLocalDate().toEpochDay() - t1.toLocalDate().toEpochDay());
			return i>0?days+1:days;
		}

		/**
		 * LocalDateTime 解析
		 * @param date
		 * @return LocalDateTime
		 */
		public static LocalDateTime parseLocalDateTime(String date){
			if(StringUtils.isEmpty(date)){
				return null;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDatePattern);
			//格式化
			LocalDateTime parse = LocalDateTime.parse(date,formatter);
			return parse;

		}
		/**
		 * 格式化LocalDateTime
		 * @param localDateTime
		 * @return
		 */
		public static Date localFormat(LocalDateTime localDateTime){
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(defaultDatePattern);
			//格式化
			String strDate = localDateTime.format(dtf);
			return parseDate(strDate);
		}
		/**
		 * 指定时间向前(-n)或向后推n天
		 * @param current java.util.Date
		 * @param days
		 * @return Date
		 */
		public static Date addDay(Date current,int days){
			Calendar calendar=new GregorianCalendar(); 
			calendar.setTime(current);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}
	    /**
		 * 
		 * 指定时间向前(-n)或向后推n天
		 * @param strdate yyyy-MM-dd
		 * @param days
		 * @return Date
		 */
		public static Date addDay(String strdate,int days){
	
			Date date=null;
			if (StringUtils.isEmpty(strdate)) {
				return null;
			}
			try {
				date=parseDate(strdate,yyyy_MM_dd);
			} catch (Exception e) {
				return null;
			}
			return addDay(date,days);
		}

		/**
		 * 
		 * 指定时间向前(-n)或向后推n小时
		 * @param current java.util.Date
		 * @param hour
		 * @return Date
		 */
		public static Date addHour(Date current,int hour){

			Calendar calendar=new GregorianCalendar(); 
			calendar.setTime(current);
			calendar.add(Calendar.HOUR, hour);
			return calendar.getTime();
		}
		
		/**
		 * 
		 * 指定时间向前(-n)或向后推n小时
		 * @param strdate yyyy-MM-dd HH,yyyy-MM-dd HH:mm:ss
		 * @param hour
		 * @return Date
		 */
		public static Date addHour(String strdate,int hour){
			Date date=null;
			if (StringUtils.isEmpty(strdate)) {
				return null;
			}
			try {
				if (strdate.indexOf(":")!=-1&&strdate.length()>13) {
					date=parseDate(strdate,yyyy_MM_dd_HH_mm_ss);
				}else{
				    date=parseDate(strdate,yyyy_MM_dd_HH);
				}
			} catch (Exception e) {
				return null;
			}
			return addHour(date,hour);
		}

		/**
		 * 
		 * 指定时间向前(-n)或向后推n分钟
		 * @param current java.util.Date
		 * @param minute
		 * @return Date
		 */
		public static Date addMinute(Date current,int minute){

			Calendar calendar=new GregorianCalendar(); 
			calendar.setTime(current);
			calendar.add(Calendar.MINUTE, minute);
			return calendar.getTime();
		}

		/**
		    * 
		    * 指定时间向前(-n)或向后推n个月份
		    * @param date   java.util.Date
		    * @param nmonth  
		    * @return Date
		    */
			public static Date addMonth(Date date, int nmonth) {
				try {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, nmonth);// 增加一个月
					return calendar.getTime();
				} catch (Exception e) {
					return null;
				}
			}
		
		/**
		 * 
		 * 指定时间向前(-n)或向后推n个月份
		 * @param strdate  yyyy-MM-dd  yyyy-MM
		 * @param n_month 
		 * @return Date
		 */
		public static Date addMonth(String strdate, int n_month) {
			Date date=null;
			if (StringUtils.isEmpty(strdate)) {
				return null;
			}
			try {
			  date=parseDate(strdate,yyyy_MM);
			} catch (Exception e) {
			}
			try {
				date=parseDate(strdate,yyyy_MM_dd);
			} catch (Exception e) {
			}
			if (date==null) {
				return null;
			}
			return addMonth(date,  n_month);
		}
		/**
         * 
         * 指定时间向前(-n)或向后推n秒
         * @param current java.util.Date
         * @param second
         * @return Date
         */
		public static Date addSecond(Date current,int second){
			Calendar calendar=new GregorianCalendar(); 
			calendar.setTime(current);
			calendar.add(Calendar.SECOND, second);
			return calendar.getTime();
		}
        /**
		 * 
		 * 指定时间向前(-n)或向后推n秒
		 * @param strdate yyyy-MM-dd HH:mm:ss
		 * @param second
		 * @return Date
		 */
		public static Date addSecond(String strdate,int second){
			Date date=null;
			if (StringUtils.isEmpty(strdate)) {
				return null;
			}
			try {
			  date=parseDate(strdate,yyyy_MM_dd_HH_mm_ss);
			} catch (Exception e) {
				return null;
			}
			return addSecond(date,second);
		}

         /**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
		 * @param v1 被除数
		 * @param v2 除数
		 * @param scale 表示表示需要精确到小数点以后几位。
		 * @return double 两个参数的商
		 */
		public static double div(double v1, double v2, int scale) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The scale must be a positive integer or zero");
			}
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}  

		/**
		 * 
		 * 日期格式化
		 * @param date   日期
		 * @param pattern  格式
		 * @return String
		 */
		public static String format(Date date, String pattern) {
			String date2 = "";
			if (StringUtils.isEmpty(pattern)) {
				pattern = yyyy_MM_dd_HH_mm_ss;
			}

			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				date2 = sdf.format(date);
			}

			return date2;
		}
		public static String format(Date date) {
			String date2 = "";
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
				date2 = sdf.format(date);
			}

			return date2;
		}

		/**
		 * 
		 * 获得系统的日期格式
		 * @return String
		 */
		public static String getDatePattern() {
			Locale locale = LocaleContextHolder.getLocale();
			try {
				defaultDatePattern = ResourceBundle.getBundle("yyyy-MM-dd",locale).getString("date.format");
			} catch (MissingResourceException mse) {
				defaultDatePattern = "yyyy-MM-dd";
			}
			return defaultDatePattern;
		}

		/**
		 * 
		 * 获取当前天
		 * @return int
		 */
		public static int getDay() {
			Calendar calendar=Calendar.getInstance(); 
			return calendar.get(Calendar.DATE);

		}


		/**
		 * 
		 * 根据日期返回对应的毫秒数
		 * @param date 日期
		 * @return long  
		 */ 
		public static long getTimeMillis(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.getTimeInMillis();
		}
		
		public static String getDateFromMills(long mills,String pattern){
			Calendar c = Calendar.getInstance();
			if (StringUtils.isEmpty(pattern)) {
				pattern = yyyy_MM_dd_HH_mm_ss;
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			c.setTimeInMillis(mills); 
			Date date = c.getTime();
			return simpleDateFormat.format(date);
		}
		/**
		 * 
		 * 获取当前月
		 * @return int
		 */
		public static int getMonth() {
			Calendar calendar=Calendar.getInstance(); 
			return calendar.get(Calendar.MONTH)+1;

		}

		/**
		 * 
		 * 获得当前时间
		 * @return Date
		 */
		public  static Date getSystemTime() {
			Calendar now = Calendar.getInstance();
			return now.getTime();
		}

		/**
		 * 
		 * 获得系统时间   格式根据系统默认格式
		 * @return String
		 */
		public static String getSystemTimeStr() {
			Date aDate = new Date();
			
			SimpleDateFormat df = null;
			String returnValue = "";
			
			if (aDate != null) {
				df = new SimpleDateFormat(getDatePattern());
				returnValue = df.format(aDate);
			}
			return (returnValue);
		}
        /**
		 * 
		 * 获得系统时间   格式根据pattern
		 * @return String
		 */
		public static String getSystemTimeStr(String pattern) {
			Date aDate = new Date();

			SimpleDateFormat df = null;
			String returnValue = "";

			if (aDate != null) {
				df = new SimpleDateFormat(pattern);
				returnValue = df.format(aDate);
			}
			return (returnValue);
		}

        /**
		 * 
		 * 获取当前年
		 * @return int
		 */
		public static int getYear() {
			Calendar calendar=Calendar.getInstance(); 
			return calendar.get(Calendar.YEAR);
		}


        /**
		 * 
		 * 日期的字符串格式转Date对象实例
		 * @param strdate   日期
		 * @param pattern  日期格式
		 * @return Date
		 */
		public static Date parseDate(String strdate, String pattern) {
			Date date=null;
			if (StringUtils.isEmpty(pattern)) {
				pattern = yyyy_MM_dd_HH_mm_ss;
			}
			try {
				if (!StringUtils.isEmpty(strdate)) {
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);
					date = sdf.parse(strdate);
				}
			} catch (ParseException e) {
				log.error("parseDate(String date, String pattern) ", e);
				e.printStackTrace();
			}
			return date;
		}

	public static Date parseDate(String strdate) {
		Date date=null;
		try {
			if (!StringUtils.isEmpty(strdate)) {
				SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
				date = sdf.parse(strdate);
			}
		} catch (ParseException e) {
			log.error("parseDate(String date, String pattern) ", e);
			e.printStackTrace();
		}
		return date;
	}

}
