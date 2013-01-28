package com.ssnn.dujiaok.biz.service.impl;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.dal.HotelDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.biz.page.QueryResult;
import com.ssnn.dujiaok.biz.service.HotelRoomService;
import com.ssnn.dujiaok.biz.service.HotelService;
import com.ssnn.dujiaok.model.HotelDO;
import com.ssnn.dujiaok.util.UniqueIDUtil;
import com.ssnn.dujiaok.util.enums.ProductEnums;

public class HotelServiceImpl implements HotelService {

	private HotelDAO hotelDAO;

	private HotelRoomService hotelRoomService;

	public void setHotelDAO(HotelDAO hotelDAO) {
		this.hotelDAO = hotelDAO;
	}

	public void setHotelRoomService(HotelRoomService hotelRoomService) {
		this.hotelRoomService = hotelRoomService;
	}

	@Override
	public HotelDO getHotel(String hotelId) {
		return hotelDAO.queryHotel(hotelId);
	}

	@Override
	public HotelDO createHotel(HotelDO hotel) {
		hotel.setProductId(UniqueIDUtil.buildUniqueId(ProductEnums.HOTEL));
		hotelDAO.insertHotel(hotel);
		return hotel;
	}

	@Override
	public HotelDO updateHotel(HotelDO hotel) {
		hotelDAO.updateHotel(hotel);
		return hotel;
	}

	@Override
	public QueryResult<HotelDO> getHotels(Map<String, Object> condition,
			Pagination pagination) {
		int count = hotelDAO.countHotels(condition);
		pagination.setTotalCount(count);

		List<HotelDO> items = hotelDAO.queryHotel(condition, pagination);

		return new QueryResult<HotelDO>(items, pagination);
	}

	@Override
	public void deleteHotel(String hotelId) {
		hotelDAO.deleteHotel(hotelId);
		hotelRoomService.deleteHotelRooms(hotelId);
	}

	@Override
	public List<HotelDO> getHotelsByProductIds(List<String> productIdList) {
		return hotelDAO.queryHotelsByProductIds(productIdList) ;
	}

}
