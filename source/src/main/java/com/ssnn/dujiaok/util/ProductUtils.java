package com.ssnn.dujiaok.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ssnn.dujiaok.model.PriceCalendarDO;
import com.ssnn.dujiaok.model.PriceCalendarDO.Item;
import com.ssnn.dujiaok.model.ProductDetailDO;


/**
 * ProductUtils
 * @author shenjia.caosj 2012-2-11
 *
 */
public class ProductUtils {
	
	private static final String PC_YMD_FORMAT = "yyyy-MM-dd" ; //价格日历格式
	private static final String PC_YM_FORMAT = "yyyy-MM" ; //价格日历格式
	/**
	 * 
	 * @param details
	 * @return
	 */
	public static List<PriceCalendarDO> getPriceCalendar(List<ProductDetailDO> details){		
		List<PriceCalendarDO> calendar = new ArrayList<PriceCalendarDO>() ;
		
		PriceCalendarDO pc = new PriceCalendarDO() ;
		pc.setSevertime("2012-02") ;
		
		List<Item> list = new ArrayList<Item>() ;
		list.add(new Item(11,("214"))) ;
		list.add(new Item(10,("222"))) ;
		list.add(new Item(9,("333"))) ;
		list.add(new Item(1,("22"))) ;
		pc.setData(list) ;
		
		calendar.add(pc) ;
		return calendar ;
	}
	
	public static List<Date> findDays(ProductDetailDO detail){
		if(detail == null){
			return null ;
		}
		if(detail.getGmtStart() == null || detail.getGmtEnd() == null){
			return null ;
		}
		if(detail.getGmtStart().after(detail.getGmtEnd())){
			return null ;
		}
		List<Date> dateList = new ArrayList<Date>() ;
		DateUtil.fillDays(dateList, detail.getGmtStart()	, detail.getGmtEnd()) ;
		return dateList ;
	}
	
	public static BigDecimal getChpestPrice(ProductDetailDO detail){
		return null ;
	}
	public static void filteInvalideProductDetail(List<ProductDetailDO> detailDOs) {
		if (detailDOs == null) {
			return;
		}
		Date nowDate = new Date();
		for (int i = detailDOs.size() - 1; i >= 0; i--) {
			ProductDetailDO detailDO = detailDOs.get(i);
			if (nowDate.after(detailDO.getGmtEnd())) {
				detailDOs.remove(i);
			}
		}
	}
	
	public static List<String> formatDate(List<Date> dates, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		List<String> result = new ArrayList<String>();
		if (dates != null) {
			for (Date temp : dates) {
				result.add(dateFormat.format(temp));
			}
		}
		return result;
	}
}

