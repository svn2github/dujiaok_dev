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
	 * 
	 * @param roomId
	 * @return
	 */
	List<HotelRoomDetailDO> queryRoomDetail(String roomId) ;
	
	/**
	 * 
	 * @param roomDetail
	 */
	void insertRoomDetail(HotelRoomDetailDO roomDetail) ;
	
	/**
	 * 
	 * @param roomId
	 */
	void deleteRoomDetails(String roomId) ;
}
