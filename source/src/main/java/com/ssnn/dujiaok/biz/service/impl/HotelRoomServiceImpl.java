package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;

import com.ssnn.dujiaok.biz.dal.HotelRoomDAO;
import com.ssnn.dujiaok.biz.dal.HotelRoomDetailDAO;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.model.HotelRoomDO;
import com.ssnn.dujiaok.model.HotelRoomDetailDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public class HotelRoomServiceImpl implements HotelRoomService{

	private HotelRoomDAO hotelRoomDAO ;
	
	private HotelRoomDetailDAO hotelRoomDetailDAO ;
	
	public void setHotelRoomDAO(HotelRoomDAO hotelRoomDAO) {
		this.hotelRoomDAO = hotelRoomDAO;
	}

	public void setHotelRoomDetailDAO(HotelRoomDetailDAO hotelRoomDetailDAO) {
		this.hotelRoomDetailDAO = hotelRoomDetailDAO;
	}

	@Override
	public HotelRoomDO getRoom(String roomId) {
		return hotelRoomDAO.queryRoom(roomId) ;
	}

	@Override
	public HotelRoomDO getRoomWithDetails(String roomId) {
		HotelRoomDO room = hotelRoomDAO.queryRoom(roomId) ;
		if(room != null){
			List<HotelRoomDetailDO> roomDetails = hotelRoomDetailDAO.queryRoomDetails(roomId) ;
			room.setRoomDetails(roomDetails) ;
		}
		return room ;
	}

	@Override
	public HotelRoomDO createRoomAndDetails(HotelRoomDO room) {
		List<HotelRoomDetailDO> details = room.getRoomDetails() ;
		if(details != null){
			room.setRoomId(UniqueIDUtil.getUniqueID(ProductEnums.HOTEL_ROOM)) ;
			hotelRoomDAO.insertRoom(room) ;
			for(HotelRoomDetailDO roomDetail : details){
				roomDetail.setRoomId(room.getRoomId()) ;
				hotelRoomDetailDAO.insertRoomDetail(roomDetail) ;
			}
		}

		return room ;
	}

	@Override
	public HotelRoomDO updateRoomAndDetails(HotelRoomDO room) {
		List<HotelRoomDetailDO> details = room.getRoomDetails() ;
		String roomId = room.getRoomId() ; 
		if(details != null){
			//删除之前的detail，重新插入
			hotelRoomDetailDAO.deleteRoomDetails(roomId) ;
			hotelRoomDAO.updateRoom(room) ;
			for(HotelRoomDetailDO roomDetail : details){
				roomDetail.setRoomId(roomId) ;
				hotelRoomDetailDAO.insertRoomDetail(roomDetail) ;
			}
		}
		return room ;
	}

}
