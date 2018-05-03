package com.yonyou.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间的工具类
 * @author caozq
 * 2017-06-27
 */
public class DateUtil {


    /**
     * 获取现在时间
     * @Title: getNowDate
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @return
     */
	public static Date getNowDate() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   ParsePosition pos = new ParsePosition(8);
	   Date currentTime_2 = formatter.parse(dateString, pos);
	   return currentTime_2;
	}
	
	/**
	 * 获取现在时间
	 * @Title: getStringDate
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @return
	 */
	public static String getStringDate() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}

	/**
	 * 获取现在时间
	 * @Title: getStringDateShort
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @return
	 */
	public static String getStringDateShort() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * @Title: getTimeShort
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @return
	 */
	public static String getTimeShort() {
	   SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	   Date currentTime = new Date();
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * @Title: strToDateLong
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * @Title: dateToStrLong
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * @Title: dateToStr
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
	/**
	   * 将短时间格式字符串转换为时间 yyyy-MM-dd 
	   * 
	   * @param strDate
	   * @return
	   */
	public static Date strToDate(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	/**
	   * 得到现在时间
	   * 
	   * @return
	   */
	public static Date getNow() {
	   Date currentTime = new Date();
	   return currentTime;
	}
	/**
	   * 提取一个月中的最后一天
	   * 
	   * @param day
	   * @return
	   */
	public static Date getLastDate(long day) {
	   Date date = new Date();
	   long date_3_hm = date.getTime() - 3600000 * 34 * day;
	   Date date_3_hm_date = new Date(date_3_hm);
	   return date_3_hm_date;
	}
	/**
	   * 得到现在时间
	   * 
	   * @return 字符串 yyyyMMdd HHmmss
	   */
	public static String getStringToday() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	/**
	   * 得到现在小时
	   */
	public static String getHour() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   String hour;
	   hour = dateString.substring(11, 13);
	   return hour;
	}
	/**
	   * 得到现在分钟
	   * 
	   * @return
	   */
	public static String getTime() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   String min;
	   min = dateString.substring(14, 16);
	   return min;
	}
	/**
	   * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	   * 
	   * @param sformat
	   *             yyyyMMddhhmmss
	   * @return
	   */
	public static String getUserDate(String sformat) {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat(sformat);
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	
	/**
	 * 时间戳转化为Sting或Date  
	 */
	public static String getData(Long data){
		 SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 return format.format(data);  
	}

}
	
