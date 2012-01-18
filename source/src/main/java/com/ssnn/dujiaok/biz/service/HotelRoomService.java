package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.model.HotelRoomDO;

/**
 * 
 * @author shenjia.caosj 2012-1-18
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
	
}
