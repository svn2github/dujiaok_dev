package com.ssnn.dujiaok.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static boolean isBetween(Date date, Date start, Date end) {
		if (date == null || start == null || end == null) {
			return false;
		}
		if (date.after(start) && date.before(end)) {
			return true;
		}
		return false;
	}
	
	/**
     * 调整指定日期的时间
     * 
     * @param date 日期对象 {@link Date}
     * @param hours 小时
     * @param minute 分
     * @param second 秒
     * @return 根据时\分\秒调整了Date后日期&时间
     */
	public static Date setTime(Date date , int h , int m , int s){
		Calendar c = Calendar.getInstance() ;
		c.setTime(date) ;
		c.set(Calendar.HOUR_OF_DAY, h) ;
        c.set(Calendar.MINUTE, m) ;
        c.set(Calendar.SECOND, s) ;
        return c.getTime() ;
	}
	
}
