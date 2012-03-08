package com.ssnn.dujiaok.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.ssnn.dujiaok.model.PriceCalendarDO;
import com.ssnn.dujiaok.model.PriceCalendarDO.Item;
import com.ssnn.dujiaok.model.ProductDetailDO;


/**
 * ProductUtils
 * @author shenjia.caosj 2012-2-11
 *
 */
public class ProductUtils {
	
	private static final String PC_YM_FORMAT = "yyyy-MM" ; //价格日历格式
	
	/**
	 * 获取价格日历
	 * @param details
	 * @return
	 */
	public static List<PriceCalendarDO> getPriceCalendar(List<ProductDetailDO> details){		
		List<PriceCalendarDO> list = new ArrayList<PriceCalendarDO>() ;
		DateFormat df = new SimpleDateFormat(PC_YM_FORMAT) ;
		if(CollectionUtils.isNotEmpty(details)){
			for(ProductDetailDO detail : details){
				List<Date> dates = findDays(detail) ;
				String price = "￥" + getChpestPrice(detail).toString() ;
				for(Date date : dates){
					String key = df.format(date) ;
					PriceCalendarDO cal = getCalendar(list, key) ;
					if(cal == null){
						cal = new PriceCalendarDO() ;
						cal.setSevertime(key) ;
						list.add(cal) ;
					}
					cal.getData().add(new Item(date.getDate() , price )) ;
				}
			}
		}
		for(PriceCalendarDO cal : list){
			cal.complete() ;
		}
		return list ;
	}
	
	private static PriceCalendarDO getCalendar(List<PriceCalendarDO> list , String key ){
		for(PriceCalendarDO cal : list){
			if(StringUtils.equals(key, cal.getSevertime())){
				return cal ;
			}
		}
		return null ;
	}
	
	/**
	 * detail 时间是否交叉
	 * @param details
	 * @return
	 */
	public static boolean isDetailDateValid(List<ProductDetailDO> details){
		return true ;
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
	
	
	public static void filteProductDetail(List<ProductDetailDO> detailDOs, Date beginDate, Date endDate) {
		
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

