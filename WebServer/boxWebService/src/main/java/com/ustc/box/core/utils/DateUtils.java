package com.ustc.box.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author gpzhang
 *
 * @date : 2014-8-21
 */

public class DateUtils {

	// private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger logger = Logger.getLogger(DateUtils.class);

	/**
	 * 初始化查询时间时间范围
	 * 
	 * @param startTime
	 * @param endTime
	 * @param defaultDelayTime
	 *            如果没有选择结束时间，默认向前推移的天数
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getFormatTimePeriod(String startTime,
			String endTime, Integer defaultDelayTime) {
		List<String> titles = null;
		Calendar ss = Calendar.getInstance();
		ss.setTime(new Date());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (endTime == null || "".equals(endTime)) {
				endTime = format.format(ss.getTime());
			} else {
				endTime = format.format(format.parse(endTime));
			}
			if (startTime == null || "".equals(startTime)) {
				ss.add(Calendar.DATE, -defaultDelayTime);
				startTime = format.format(ss.getTime());
			} else {
				startTime = format.format(format.parse(startTime));
			}

			titles = getPeriodDays(startTime, endTime, format);
		} catch (Exception e) {
			logger.error("context", e); // Compliant
		}

		return titles;
	}

	/**
	 * 初始化周、月的数据，得到连续一个范围内周和月的数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @param cycleType
	 * @return
	 */
	public static List<String> getFormatWeekPeriod(String startTime,
			String endTime, String cycleType) {
		List<String> titles = new ArrayList<String>();
		Date st = null;
		Date en = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			st = format.parse(startTime);
			en = format.parse(endTime);

		} catch (Exception e) {
			logger.error(e);
		}
		if (st != null && en != null && st.after(en))
			return null;
		Calendar ss = Calendar.getInstance();
		ss.setTime(st);
		Calendar ee = Calendar.getInstance();
		ee.setTime(en);
		do {
			if (cycleType.equals("2")) {
				titles.add(format.format(ss.getTime()));
				ss.add(Calendar.WEEK_OF_YEAR, 1);
			} else if (cycleType.equals("3")) {
				titles.add(format.format(ss.getTime()));
				ss.add(Calendar.MONTH, 1);
			}
		} while (ss.compareTo(ee) <= 0);
		return titles;
	}

	/**
	 * 根据某个日期得到周一的时间
	 * 
	 * @param time
	 * @param cycleType
	 * @return
	 */
	public static String getWeekOfMondayTime(String time, String cycleType) {
		Date dt = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dt = format.parse(time);
		} catch (ParseException e) {
			logger.error("时间格式不对");
		}
		if ("2".equals(cycleType)) {
			Calendar ss = Calendar.getInstance();
			ss.setTime(dt);
			ss.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			return format.format(ss.getTime());
		} else {
			return time;
		}
	}

	/**
	 * 获取一段时间，防止日志中缺少某天的数据
	 * 
	 * @param start
	 * @param end
	 * @param format
	 * @param format2
	 * @return
	 * @throws ParseException
	 */
	private static List<String> getPeriodDays(String start, String end,
			DateFormat format) throws ParseException {
		Date st = format.parse(start);
		Date en = format.parse(end);
		List<String> list = new ArrayList<String>();
		if (st.after(en))
			return null;
		Calendar ss = Calendar.getInstance();
		ss.setTime(st);
		Calendar ee = Calendar.getInstance();
		ee.setTime(en);
		do {
			list.add(format.format(ss.getTime()));
			ss.add(Calendar.DATE, 1);
		} while (ss.compareTo(ee) <= 0);
		return list;
	}

	/**
	 * 将全数字格式时间转成标准格式"yyyy-MM-dd"
	 * 
	 * @param date
	 *            "20140808"格式
	 * @return
	 */
	public static String getFormatTime(String date) {
		DateFormat sdf = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (Pattern.matches("^\\d{4}-?\\d{2}$", date)) {
			sdf = new SimpleDateFormat("yyyyMM");
		} else {
			sdf = new SimpleDateFormat("yyyyMMdd");
		}
		try {
			Date result = sdf.parse(date);
			return format.format(result);
		} catch (ParseException e) {
			logger.error("context", e); // Compliant
			return null;
		}
	}

	
	
	/**
	 * 得到某一天在某一年多少周
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static int getWeekofYear(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(specifiedDay);
		} catch (ParseException e) {
			logger.error("context", e); // Compliant
		}
		c.setTime(date);
		int week = c.get(Calendar.WEEK_OF_YEAR);

		return week;
	}

	/**
	 * @param specifiedDay
	 * @param offset
	 * @return
	 */
	public static String getSpecifiedDay(String specifiedDay, int offset) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(specifiedDay);
		} catch (ParseException e) {
			logger.error("context", e); // Compliant
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + offset);

		return df.format(c.getTime());
	}

	/**
	 * 根据日期和时间参数，得到类似2014年第31周(07/27-08/02)的数据
	 * 
	 * @param cycleFlag
	 * @param date
	 * @return
	 */
	public static String getDateNameByCycleFlag(String cycleFlag, String date) {
		if ("1".equals(cycleFlag)) {
			return date.toString().substring(0, 10);
		} else if ("2".equals(cycleFlag)) {
			int weekofYear = DateUtils.getWeekofYear(date.substring(0, 10));
			String weekStart = date.substring(5, 7) + "/"
					+ date.substring(8, 10);
			String weeklast = DateUtils.getSpecifiedDay(date.substring(0, 10),
					6);
			String weekEnd = weeklast.substring(5, 7) + "/"
					+ weeklast.substring(8, 10);
			return date.substring(0, 4) + "年第" + weekofYear + "周(" + weekStart
					+ "-" + weekEnd + ")";
		} else if ("3".equals(cycleFlag)) {
			return date.substring(0, 4) + "年" + date.substring(5, 7) + "月";
		} else {
			return "";
		}
	}

	/**
	 * 获取昨天的时间
	 * 
	 * @return
	 */
	public static String getYesterDayTime() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return format.format(cal.getTime());
	}

	/**
	 * 获取昨天的时间
	 * 
	 * @return
	 */
	public static String getYesterDayHour() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String result = format.format(cal.getTime());
		return result.substring(11, 13);
	}

	/**
	 * 获取今天的时间
	 * 
	 * @return
	 */
	public static String getCurrentDayTime() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
	
	 public static int getLastDay(String beginDate,String endDate) throws ParseException{
	     SimpleDateFormat sim = new SimpleDateFormat( "yyyy-MM-dd");
	     Date d1 = sim.parse(beginDate); 
	     Date d2 = sim.parse(endDate);
	     return (int) ((d2.getTime() - d1.getTime()) / (3600L * 1000 * 24));
	    }

	 public static String getLastHour(String beginDate,String endDate){
		  SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
	        long nh = 1000 * 60 * 60;// 一小时的毫秒数  
	        long nm = 1000 * 60;// 一分钟的毫秒数  
	        long ns = 1000;// 一秒钟的毫秒数  
	        long diff;  
	        long day = 0;  
	        long hour = 0;  
	        long min = 0;  
	        long sec = 0;  
	        // 获得两个时间的毫秒时间差异  
	        try {  
	            diff = sd.parse(endDate).getTime() - sd.parse(beginDate).getTime();  
	            day = diff / nd;// 计算差多少天  
	            hour = diff % nd / nh + day * 24;// 计算差多少小时  
	            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟  
	            sec = diff % nd % nh % nm / ns;// 计算差多少秒  
	            // 输出结果  
	            System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"  
	                    + (min - day * 24 * 60) + "分钟" + sec + "秒。");  
	            return String.valueOf(hour);
	  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	            return "";
	        }  
	 }
	


}
