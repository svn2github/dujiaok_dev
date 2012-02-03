package com.ssnn.dujiaok.biz.dal;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.model.product.Product2;
import com.ssnn.dujiaok.model.product.detail.HotelDetail;

/**
 * 酒店DAO
 * @author shenjia.caosj 2012-1-17
 *
 */
public interface HotelDAO {

	/**
	 * 
	 * @param hotelId
	 * @return
	 */
	HotelDO queryHotel(String hotelId) ;
	/**
	 * 获取产品相关的酒店.
	 * @param product2 .
	 * @return .
	 */
	public List<HotelDetail> getHotelByProducts(Product2 product2);
	/**
	 * 
	 * @param Hotel
	 */
	void insertHotel(HotelDO hotel) ;
	
	/**
	 * 
	 * @param Hotel
	 */
	void updateHotel(HotelDO hotel) ;
	
	/**
	 * 查询Hotel
	 * @param condition 条件  key=字段名，value=字段值
	 * @param pagination 分页BEAN
	 * @return
	 */
	List<HotelDO> queryHotel(Map<String,Object> condition , Pagination pagination) ;
	
	/**
	 * 
	 * @param condition
	 * @return
	 */
	int countHotels(Map<String,Object> condition) ;
	
	/**
	 * 
	 * @param hotelId
	 */
	void deleteHotel(String hotelId) ;
}
