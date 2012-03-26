package com.ssnn.dujiaok.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.ssnn.dujiaok.model.DetailItemDO;
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
		
		Date start = new Date() ;
		Date end = new Date() ;
		end = org.apache.commons.lang.time.DateUtils.addMonths(end, 1) ;
		
		List<DetailItemDO> itemList = getDetailItems(details,start , end) ;
		if(CollectionUtils.isEmpty(itemList)){
			return list ;
		}
		DateFormat monthFormat = new SimpleDateFormat(PC_YM_FORMAT) ;
		for(DetailItemDO item : itemList){
			String price = "￥" + item.getBottomPrice() ;
			Date date = item.getDate() ;
			String key = monthFormat.format(date) ;
			PriceCalendarDO cal = getCalendar(list, key) ;
			if(cal == null){
				cal = new PriceCalendarDO() ;
				cal.setSevertime(key) ;
				list.add(cal) ;
			}
			cal.getData().add(new Item(date.getDate() , price )) ;
		}
				
		return list ;
	}
	
	public static List<DetailItemDO> getDetailItems(List<ProductDetailDO> details , Date start , Date end){
		start = DateUtils.setTime(start, 0, 0, 0) ;
		end = DateUtils.setTime(end, 23, 59, 59) ;
		if(details == null || details.isEmpty() || start.after(end)){
			return new ArrayList<DetailItemDO>() ;
		}
		//
		List<DetailItemDO> detailItems = new ArrayList<DetailItemDO>() ;
		
		for(ProductDetailDO d : details){
			List<Date> subList = ProductUtils.findDays(d) ;
			BigDecimal bottomPrice = ProductUtils.calcBottomPrice(d) ;
			if(CollectionUtils.isEmpty(subList)){
				continue ;
			}
			//过滤过期时间
			for(Iterator<Date> i = subList.iterator() ;i.hasNext() ;){
				Date date = i.next() ;
				if(!DateUtils.isBetween(date, start, end)){
					i.remove() ;
				}
			}
			for(Date date : subList){
				detailItems.add(new DetailItemDO(d.getId()	, date, bottomPrice , d)) ;
			}
		}
		//排序
		Collections.sort(detailItems) ;
		DetailItemDO prev = null ;
		for(Iterator<DetailItemDO> i = detailItems.iterator() ;i.hasNext() ;){
			DetailItemDO item = i.next() ;
			//时间范围
			if(item == null){
				i.remove() ;
			}
			//去重复
			if(prev != null){
				if(prev.equals(item)){
					i.remove() ;
				}
			}
			prev = item ;
		}
		return detailItems ;
	}
	
	private static PriceCalendarDO getCalendar(List<PriceCalendarDO> list , String key ){
		for(PriceCalendarDO cal : list){
			if(StringUtils.equals(key, cal.getSevertime())){
				return cal ;
			}
		}
		return null ;
	}
	
	public static BigDecimal calcBottomPrice(List<ProductDetailDO> details){
		BigDecimal bottomPrice = null ;
		if(!CollectionUtils.isEmpty(details)){
			for(ProductDetailDO d : details){
				if(d == null){
					continue ;
				}
				BigDecimal bd = calcBottomPrice(d) ;
				if(bottomPrice == null){
					bottomPrice = bd ;
					continue ;
				}
				if(bottomPrice.compareTo(bd) == -1){
					bottomPrice = bd ;
					continue ;
				}
			}
		}
		return bottomPrice ;
	}
	
	public static BigDecimal calcBottomPrice(ProductDetailDO detail){
		if(detail.getDoublePrice() == null){
			return detail.getPrice() ;
		}
		BigDecimal singlePrice = detail.getDoublePrice() ;
		singlePrice = singlePrice.divide(new BigDecimal(2)) ;
		return singlePrice.compareTo(detail.getPrice()) < 0 ? singlePrice : detail.getPrice() ;
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
	
	
	
	public static ProductDetailDO getFirstDetail(List<ProductDetailDO> details){
		if(details == null || details.isEmpty()){
			return null ;
		}
		for(ProductDetailDO detail : details){
			if(detail == null){
				continue ;
			}
			Date start = detail.getGmtStart() ;
			Date end = detail.getGmtEnd() ;
			if(start == null || end == null){
				continue ;
			}
			if(start.after(end)){
				continue ;
			}
			if(DateUtils.isBetween(new Date(), start, end)){
				return detail ;
			}
		}
		return null ;
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

}

