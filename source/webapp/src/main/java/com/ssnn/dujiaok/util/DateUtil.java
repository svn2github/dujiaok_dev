package com.ssnn.dujiaok.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.util.string.StringUtil;

public final class DateUtil {
	public static void fillDays(List<Date> dateList, Date fromDate, Date toDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(fromDate);
		setDay(startCalendar);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(toDate);
		setDay(endCalendar);
		while(!startCalendar.after(endCalendar)) {
			dateList.add(startCalendar.getTime());
			startCalendar.add(Calendar.DATE, 1);
		}
	}
	private static void setDay(Calendar calendar) {
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	public static Date formatDate(String dateStr, String format) {
		String temp = StringUtil.isEmpty(format) ? "yyyy-MM-dd" : format;
		DateFormat tempFormat = new SimpleDateFormat(temp);
		try {
			return tempFormat.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<Date> minus(List<Date> dates1, List<Date> dates2) {
		List<Date> tempDates = new ArrayList<Date>(dates1);
		for (Date temp : dates2) {
			tempDates.remove(temp);
		}
		return tempDates;
	}
}
