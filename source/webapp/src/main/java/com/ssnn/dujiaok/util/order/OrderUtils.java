package com.ssnn.dujiaok.util.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ssnn.dujiaok.model.AbstractProduct;
import com.ssnn.dujiaok.model.DetailItemDO;
import com.ssnn.dujiaok.model.OrderDO;
import com.ssnn.dujiaok.util.DateUtil;
import com.ssnn.dujiaok.util.DateUtils;
import com.ssnn.dujiaok.util.ProductUtils;
import com.ssnn.dujiaok.util.enums.OrderStatusEnums;
import com.ssnn.dujiaok.util.enums.PayStatusEnums;

public final class OrderUtils {
	
	public static void setOrderEndDateWithStart(OrderDO orderDO, int tourDays) {
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
		orderDO.setStatus(OrderStatusEnums.UNPAID.getName());
		orderDO.setPayStatus(PayStatusEnums.UNPAID.getName());
	}
	
	public static String getOrderInfoDesc(OrderDO order) {
		StringBuilder info = new StringBuilder();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (order.getProductId().startsWith("ZJ")) {
			info.append("出行日期：").append(format.format(order.getGmtOrderStart())).append(" 至 ")
			  .append(format.format(order.getGmtOrderEnd()));
			info.append("<br/>成人：").append(order.getCount());
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
	
	public static BigDecimal[] getRoomPrice(AbstractProduct roomDO, Date checkinDate, Date checkoutDate) {
		checkinDate = DateUtils.setTime(checkinDate, 0, 0, 0);
		checkoutDate = DateUtils.setTime(checkoutDate, 0, 0, 0);
		BigDecimal[] temp = new BigDecimal[] {new BigDecimal("0"), new BigDecimal("0")};
		
		for (DetailItemDO itemDO : roomDO.getDefaultDetailItems()) {
			if (!itemDO.getDate().before(checkinDate) && itemDO.getDate().before(checkoutDate)) {
				temp[0] = temp[0].add(itemDO.getBottomPrice());
				temp[1] = temp[1].add(roomDO.getMarketPrice());
			}
		}
		return temp;
	}
	
	public static List<String> getCantCheckInDate(List<DetailItemDO> itemDOs, Date checkinDate, Date checkoutDate) {
		checkinDate = DateUtils.setTime(checkinDate, 0, 0, 0);
		checkoutDate = DateUtils.setTime(checkoutDate, 0, 0, 0);
		List<Date> dateList1 = new ArrayList<Date>();
		DateUtil.fillDays(dateList1, checkinDate, checkoutDate);
		List<Date> dateList2 = new ArrayList<Date>();
		for (DetailItemDO itemDO : itemDOs) {
			dateList2.add(itemDO.getDate());
		}
		List<Date> missDates = DateUtil.minus(dateList1, dateList2);
		List<String> result = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Date date: missDates) {
			result.add(dateFormat.format(date));
		}
		return result;
	}
}
