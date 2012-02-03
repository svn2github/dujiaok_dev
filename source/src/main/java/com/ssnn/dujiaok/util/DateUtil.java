package com.ssnn.dujiaok.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DateUtil {
	public static void fillDays(List<Date> dateList, Date fromDate, Date toDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(fromDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(toDate);
		while(!startCalendar.after(endCalendar)) {
			dateList.add(startCalendar.getTime());
			startCalendar.add(Calendar.DATE, 1);
		}
	}
}
