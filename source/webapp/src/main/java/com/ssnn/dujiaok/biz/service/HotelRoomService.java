package com.ssnn.dujiaok.biz.service;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.model.HotelRoomDO;

/**
 * 
 * @author langben 2012-1-18
 *
 */
public interface HotelRoomService {

	/**
	 * 查询房间
	 * @param roomId
	 * @return
	 */
	HotelRoomDO getRoom(String roomId) ;
	
	/**
	 * 查询房间,包括detail
	 * @param roomId
	 * @return
	 */
	HotelRoomDO getRoomWithDetails(String roomId) ;
	
	/**
	 * 根据酒店获取房间
	 * @param hotelId
	 * @return
	 */
	List<HotelRoomDO> getRooms(String hotelId) ;
	
	/**
	 * 发布房间，包含detail
	 * @param room
	 * @return
	 */
	HotelRoomDO createRoomAndDetails(HotelRoomDO room) ;
	
	/**
	 * 更新房间，包含detail
	 * @param room
	 * @return
	 */
	HotelRoomDO updateRoomAndDetails(HotelRoomDO room) ;
	
	/**
	 * 查询
	 * @param condition
	 * @param pagination
	 * @return
	 */
	QueryResult<HotelRoomDO> getRooms(Map<String,Object> condition , Pagination pagination) ;
	
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
