package com.ssnn.dujiaok.util.order;

import java.util.Calendar;
import java.util.Date;

import com.ssnn.dujiaok.model.OrderDO;

public final class OrderUtils {
	
	public static void setSelfDriveOrderStartEndDate(OrderDO orderDO, int tourDays) {
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.setTime(orderDO.getGmtOrderStart());
		tempCalendar.set(Calendar.HOUR, 0);
		tempCalendar.set(Calendar.MINUTE, 0);
		tempCalendar.set(Calendar.SECOND, 0);
		tempCalendar.set(Calendar.MILLISECOND, 0);
		orderDO.setGmtOrderStart(tempCalendar.getTime());
		tempCalendar.add(Calendar.DAY_OF_YEAR, tourDays);
		orderDO.setGmtOrderEnd(tempCalendar.getTime());
	}
	public static boolean checkOrderKeyInfo(OrderDO orderDO) {
		return orderDO.getCount() >= 1 && orderDO.getProductId() != null
		  && orderDO.getProductDetailId() != null && orderDO.getGmtOrderStart() != null
		  && orderDO.getGmtOrderEnd() != null && !orderDO.getGmtOrderStart().after(orderDO.getGmtOrderEnd());
	}
	
	public static void setOrderDefaultValue(OrderDO orderDO) {
		if (orderDO.getGmtCreate() == null) {
			orderDO.setGmtCreate(new Date());
		}
		if (orderDO.getGmtModified() == null) {
			orderDO.setGmtModified(new Date());
		}
		if (orderDO.getStatus() == null) {
			orderDO.setStatus("待付款");
		}
		if (orderDO.getStatusDetail() == null) {
			orderDO.setStatusDetail("下单待付款");
		}
		if (orderDO.getPayStatus() == null) {
			orderDO.setPayStatus("UNPAY");
		}
	}
}
