package com.ssnn.dujiaok.util.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ssnn.dujiaok.constant.OrderStatus;
import com.ssnn.dujiaok.constant.PayStatus;
import com.ssnn.dujiaok.model.OrderDO;

public final class OrderUtils {
	
	public static void setSelfDriveOrderEndDateWithStart(OrderDO orderDO, int tourDays) {
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
		  && (orderDO.getGmtOrderEnd() == null || orderDO.getGmtOrderStart().before(orderDO.getGmtOrderEnd()));
	}
	
	public static void setOrderDefaultValue(OrderDO orderDO) {
		orderDO.setStatus(OrderStatus.UNPAY.toString());
		orderDO.setPayStatus(PayStatus.TRADE_UNPAY.toString());
	}
	
	public static String getOrderInfoDesc(OrderDO order) {
		StringBuilder info = new StringBuilder();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (order.getProductId().startsWith("ZJ")) {
			info.append("出行日期：").append(format.format(order.getGmtOrderStart())).append(" 至 ")
			  .append(format.format(order.getGmtOrderEnd()));
			info.append("<br/>成人：").append(order.getCount()).append("位");
			if (order.getSecondaryCount() > 0) {
				info.append("  儿童：").append(order.getSecondaryCount());
			}
		} else if (order.getProductId().startsWith("FJ")) {
			info.append("入住日期：").append(format.format(order.getGmtOrderStart())).append("  离店日期：")
			  .append(format.format(order.getGmtOrderEnd())).append("  <br/>预定房间数：").append(order.getCount());
		} else if (order.getProductId().startsWith("MP")) {
			info.append("使用日期：").append(format.format(order.getGmtOrderStart())).append("  预定数量：")
			  .append(order.getCount());
		}
		return info.toString();
	}
}
