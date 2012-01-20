package com.ssnn.dujiaok.biz.service;

import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.model.HotelDO;

/**
 * 
 * @author shenjia.caosj 2012-1-17
 *
 */
public interface HotelService {

	/**
	 * 获取hotelId
	 * @param hotelId
	 * @return
	 */
	HotelDO getHotel(String hotelId) ;
	
	/**
	 * 创建hotel
	 * @param hotel
	 */
	HotelDO createHotel(HotelDO hotel) ;
	
	/**
	 * 更新hotel
	 * @param hotel
	 */
	HotelDO updateHotel(HotelDO hotel) ;
	
	/**
	 * 查询
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<HotelDO> getHotels(Map<String,Object> condition , Pagination pagination) ;
}
