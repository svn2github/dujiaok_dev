package com.ssnn.dujiaok.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ssnn.dujiaok.model.PriceCalendarDO;
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
		DateFormat df = new SimpleDateFormat(PC_YM_FORMAT) ;
		if(CollectionUtils.isNotEmpty(details)){
			for(ProductDetailDO detail : details){
				List<Date> dates = findDays(detail) ;
				for(Date date : dates){
					String key = df.format(date) ;
					
				}
			}
		}
		
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
		if(detail.getDoublePrice() == null){
			return detail.getPrice() ;
		}
		BigDecimal singlePrice = detail.getDoublePrice() ;
		singlePrice = singlePrice.divide(new BigDecimal(2)) ;
		return singlePrice.compareTo(detail.getPrice()) < 0 ? singlePrice : detail.getPrice() ;
	}

	
	//detail 取3个月内
	public static Date getDetailValidEnd(){
		Calendar c = Calendar.getInstance() ;
		c.add(Calendar.MONTH, 3) ;
		return c.getTime() ;
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

