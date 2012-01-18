package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.HotelRoomDetailDO;

/**
 * 酒店房间Detail
 * @author shenjia.caosj 2012-1-18
 *
 */
public interface HotelRoomDetailDAO {

	/**
	 * 查询 RoomDetail
	 * @param roomId
	 * @return
	 */
	List<HotelRoomDetailDO> queryRoomDetails(String roomId) ;
	
	/**
	 * 插入 RoomDetail
	 * @param roomDetail
	 */
	void insertRoomDetail(HotelRoomDetailDO roomDetail) ;
	
	/**
	 * 删除 RoomDetail
	 * @param roomId
	 */
	void deleteRoomDetails(String roomId) ;
}
