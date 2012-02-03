package com.ssnn.dujiaok.biz.dal;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.product.Product2;
import com.ssnn.dujiaok.model.product.detail.HotelRoomDetail;

/**
 * 酒店房间DAO
 * @author shenjia.caosj 2012-1-17
 *
 */
public interface HotelRoomDAO {

	/**
	 * 查询room
	 * @param roomId
	 * @return
	 */
	HotelRoomDO queryRoom(String roomId) ;
	/**
	 * 查询产品相关的酒店房间信息.
	 * @param product .
	 * @return
	 */
	List<HotelRoomDetail> getHotelRoomWithProducts(Product2 product);

	/**
	 * 
	 * @param hotelId
	 * @return
	 */
	List<HotelRoomDO> queryRooms(String hotelId) ;
	
	/**
	 * 插入room
	 * @param HotelRoom
	 */
	void insertRoom(HotelRoomDO room) ;
	
	/**
	 * 更新room
	 * @param HotelRoom
	 */
	void updateRoom(HotelRoomDO room) ;
	
	/**
	 * 查询HotelRoom
	 * @param condition 条件  key=字段名，value=字段值
	 * @param pagination 分页BEAN
	 * @return
	 */
	List<HotelRoomDO> queryRooms(Map<String,Object> condition , Pagination pagination) ;
	
	/**
	 * 
	 * @param condition
	 * @param pagination
	 * @return
	 */
	int countRooms(Map<String,Object> condition) ;
	
	/**
	 * 
	 * @param roomId
	 */
	void deleteHotelRoom(String roomId) ;
	
	/**
	 * 
	 * @param hotelId
	 */
	void deleteHotelRooms(String hotelId) ;
}
